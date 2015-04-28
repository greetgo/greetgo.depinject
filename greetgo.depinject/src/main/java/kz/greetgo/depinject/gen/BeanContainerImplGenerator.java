package kz.greetgo.depinject.gen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import kz.greetgo.depinject.gen.ClassContent.PrintBlock;
import kz.greetgo.depinject.gen.scanner.ClassScanner;
import kz.greetgo.depinject.gen.scanner.ClassScannerDef;
import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.BeanConfig;
import kz.greetgo.depinject.src.BeanContainer;
import kz.greetgo.depinject.src.BeanGetter;
import kz.greetgo.depinject.src.HasAfterInject;
import kz.greetgo.depinject.src.Include;
import kz.greetgo.depinject.src.ScanBeans;

public class BeanContainerImplGenerator {
  private final ClassScanner classScanner = new ClassScannerDef();
  
  public Class<?> beanContainerIface;
  
  final Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
  
  private final Set<Class<?>> scannedConfigs = new HashSet<Class<?>>();
  
  public String srcDir, packageName, implClassName;
  
  void findBeanClassSet() throws IOException {
    if (!beanContainerIface.isInterface()) throw new NoInterface();
    if (!BeanContainer.class.isAssignableFrom(beanContainerIface)) throw new NoBeanContainer();
    
    Include include = beanContainerIface.getAnnotation(Include.class);
    if (include == null) throw new NoInclude();
    
    scannedConfigs.clear();
    
    for (Class<?> config : include.value()) {
      appendAllBeansFromConfig(config);
    }
    
  }
  
  private void appendAllBeansFromConfig(Class<?> config) throws IOException {
    BeanConfig beanConfig = config.getAnnotation(BeanConfig.class);
    if (beanConfig == null) throw new NoBeanConfig();
    
    if (scannedConfigs.contains(config)) return;
    scannedConfigs.add(config);
    
    ScanBeans scanBeans = config.getAnnotation(ScanBeans.class);
    if (scanBeans != null) {
      for (String subpath : scanBeans.value()) {
        scanPathForBeans(config, subpath);
      }
    }
    
    Include include = config.getAnnotation(Include.class);
    if (include != null) {
      for (Class<?> subconfig : include.value()) {
        appendAllBeansFromConfig(subconfig);
      }
    }
    
  }
  
  private void scanPathForBeans(Class<?> config, String subpath) throws IOException {
    String packageName = config.getPackage().getName();
    String scanPackageName = applySubpath(packageName, subpath);
    for (Class<?> cl : classScanner.scanPackage(scanPackageName)) {
      if (cl.getAnnotation(Bean.class) != null) {
        beanClassSet.add(cl);
      }
    }
  }
  
  private String applySubpath(String packageName, String subpath) {
    if (subpath == null) return packageName;
    if (subpath.length() == 0) return packageName;
    if (".".equals(subpath)) return packageName;
    if (subpath.startsWith(".")) {
      subpath = subpath.substring(1);
    }
    return packageName + '.' + subpath;
  }
  
  private final ClassContent content = new ClassContent();
  
  public void generate() throws Exception {
    
    generateContent();
    
    String packagePath = packageName.replaceAll("\\.", "/");
    File outFile = new File(srcDir + '/' + packagePath + '/' + implClassName + ".java");
    outFile.getParentFile().mkdirs();
    
    PrintStream out = new PrintStream(outFile, "UTF-8");
    try {
      out.print(content.toString());
    } finally {
      out.close();
    }
  }
  
  private static String rndId() {
    String s = UUID.randomUUID().toString().replaceAll("-", "");
    return s.substring(0, 10);
  }
  
  private void generateContent() throws Exception {
    findBeanClassSet();
    
    content.addImplement(beanContainerIface);
    content.name = implClassName;
    content.packageName = packageName;
    
    for (Method method : beanContainerIface.getMethods()) {
      if (method.getParameterTypes().length > 0) {
        throw new BeanContainerMethodCannotHasAnyParameters(method);
      }
      Class<?> returnType = method.getReturnType();
      Class<?> beanClass = findBeanClassFor(returnType);
      String retTypeStr = content.imp(returnType);
      PrintBlock pb = content.newPrintBlock();
      pb.pr("@Override public ").pr(retTypeStr).pr(" ").pr(method.getName()).prn("() {");
      pb.moreIndent();
      pb.pr("return ").pr(prepareField(beanClass)).prn(".get();");
      pb.lessIndent();
      pb.prn("}");
    }
    
  }
  
  private final Map<Class<?>, String> preparedFieldMap = new HashMap<>();
  
  private String prepareField(Class<?> beanClass) {
    {
      String fieldName = preparedFieldMap.get(beanClass);
      if (fieldName != null) return fieldName;
    }
    
    String fieldName = "field_" + beanClass.getSimpleName() + "_" + rndId();
    preparedFieldMap.put(beanClass, fieldName);
    
    createField(fieldName, beanClass);
    
    return fieldName;
  }
  
  private Class<?> findBeanClassFor(Class<?> beaningClass) {
    
    Class<?> ret = null;
    
    for (Class<?> bean : beanClassSet) {
      if (beaningClass.isAssignableFrom(bean)) {
        if (ret != null) throw new MoreThenOneBeanClassIsAssignable(beaningClass, bean, ret);
        ret = bean;
      }
    }
    
    if (ret == null) throw new NoMatchingBeanFor(beaningClass);
    
    return ret;
  }
  
  private void createField(String fieldName, Class<?> beanClass) {
    Bean bean = beanClass.getAnnotation(Bean.class);
    boolean singleton = bean.singleton();
    
    PrintBlock pb = content.newPrintBlock();
    
    String beanClassStr = content.imp(beanClass);
    String beanGetterStr = content.imp(BeanGetter.class);
    
    pb.pr("private final ").pr(beanGetterStr).pr("<").pr(beanClassStr).pr("> ");
    pb.pr(fieldName).pr(" = new ").pr(beanGetterStr).pr("<").pr(beanClassStr).prn("> (){");
    pb.moreIndent();
    
    if (singleton) {
      pb.pr(beanClassStr).prn(" instance = null;");
    }
    
    pb.prn("@Override");
    pb.prn("public " + beanClassStr + " get() {");
    pb.moreIndent();
    
    if (singleton) {
      pb.prn("if (instance != null) return instance;");
    }
    
    pb.pr(beanClassStr).pr(" localInstance = new ").pr(beanClassStr).prn("();");
    
    injectDependencies(pb, "localInstance", beanClass);
    
    if (HasAfterInject.class.isAssignableFrom(beanClass)) {
      pb.prn("try {");
      pb.prn("  localInstance.afterInject();");
      pb.prn("} catch (Exception e) {");
      pb.prn("  if (e instanceof RuntimeException) throw (RuntimeException)e;");
      pb.prn("  throw new RuntimeException(e);");
      pb.prn("}");
    }
    
    if (singleton) {
      pb.prn("instance = localInstance;");
    }
    
    pb.prn("return localInstance;");
    pb.lessIndent();
    pb.prn("}");
    pb.lessIndent();
    pb.prn("};");
  }
  
  private void injectDependencies(PrintBlock pb, String var, Class<?> beanClass) {
    for (Field field : beanClass.getFields()) {
      if (field.getType() == BeanGetter.class) {
        injectField(pb, var, field);
      }
    }
  }
  
  private void injectField(PrintBlock pb, String var, Field field) {
    ParameterizedType type = (ParameterizedType)field.getGenericType();
    Class<?> inType = (Class<?>)type.getActualTypeArguments()[0];
    Class<?> beanClass = findBeanClassFor(inType);
    String beanGetterStr = content.imp(BeanGetter.class);
    String inTypeStr = content.imp(inType);
    pb.pr(var).pr(".").pr(field.getName()).pr(" = ");
    if (inType != beanClass) {
      pb.pr("(").pr(beanGetterStr).pr("<").pr(inTypeStr).pr(">)(Object)");
    }
    pb.pr(prepareField(beanClass)).prn(";");
  }
}
