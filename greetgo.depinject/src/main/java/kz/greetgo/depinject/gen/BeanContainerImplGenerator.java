package kz.greetgo.depinject.gen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
import kz.greetgo.depinject.src.gwtrpc.InvokeServiceAsync;
import kz.greetgo.depinject.src.gwtrpc.SyncAsyncConverter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
    if (include == null) throw new NoInclude(beanContainerIface);
    
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
    dummyCheck(outFile.getParentFile().mkdirs());
    
    PrintStream out = new PrintStream(outFile, "UTF-8");
    try {
      out.print(content.toString());
    } finally {
      out.close();
    }
  }
  
  private static void dummyCheck(boolean tmp) {}
  
  public void generateTo(PrintWriter writer) throws Exception {
    generateContent();
    writer.print(content.toString());
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
      String retTypeStr = content.imp(returnType);
      PrintBlock pb = content.newPrintBlock();
      pb.pr("@Override public ").pr(retTypeStr).pr(" ").pr(method.getName()).prn("() {");
      pb.moreIndent();
      pb.pr("return ").pr(prepareField(returnType).accept()).prn(";");
      pb.lessIndent();
      pb.prn("}");
    }
    
  }
  
  private class PreparedField {
    final Class<?> type;
    final String fieldName;
    final Class<?> beanClass;
    String getterFieldName;
    
    public PreparedField(Class<?> type, String fieldName, Class<?> beanClass, String getterFieldName) {
      this.type = type;
      this.fieldName = fieldName;
      this.beanClass = beanClass;
      this.getterFieldName = getterFieldName;
    }
    
    public String accept() {
      if (fieldName == null) return getterFieldName() + ".get()";
      return fieldName;
    }
    
    String getterFieldName() {
      if (getterFieldName != null) return getterFieldName;
      
      getterFieldName = fieldName + "_getter";
      PrintBlock p = content.newPrintBlock();
      String getterName = content.imp(BeanGetter.class);
      String typeName = content.imp(type);
      p.pr("private final ").pr(getterName).pr("<").pr(typeName).pr("> ").pr(getterFieldName);
      p.pr(" = new ").pr(getterName).pr("<").pr(typeName).prn("> (){");
      p.moreIndent();
      p.prn("@Override");
      p.pr("public ").pr(typeName).prn(" get() {");
      p.pr("  return ").pr(fieldName).prn(";");
      p.prn("}");
      p.lessIndent();
      p.prn("};");
      
      return getterFieldName;
    }
    
    public boolean needGetterTypeConvert() {
      if (beanClass == null) return false;
      return beanClass != type;
    }
  }
  
  private final Map<Class<?>, PreparedField> preparedFieldMap = new HashMap<>();
  
  private PreparedField prepareField(Class<?> fieldType) throws Exception {
    {
      PreparedField pfield = preparedFieldMap.get(fieldType);
      if (pfield != null) return pfield;
    }
    
    PreparedField pfield = createField(fieldType);
    
    preparedFieldMap.put(fieldType, pfield);
    
    return pfield;
  }
  
  private PreparedField createField(Class<?> fieldType) throws Exception {
    {
      Class<?> beanClass = findBeanClassFor(fieldType);
      if (beanClass != null) return createBeanField(fieldType, beanClass);
    }
    
    if (InvokeServiceAsync.class.isAssignableFrom(fieldType)) {
      return createServiceField(fieldType);
    }
    
    throw new NoMatchingBeanFor(fieldType);
  }
  
  private static final String ASYNC = "Async";
  
  private Class<?> calcSyncClass(Class<?> asyncClass) throws Exception {
    String name = asyncClass.getName();
    if (!name.endsWith(ASYNC)) {
      throw new InvokeServiceAsyncMustEndWithAsync(asyncClass);
    }
    String syncClassName = name.substring(0, name.length() - ASYNC.length());
    return Class.forName(syncClassName);
  }
  
  private PreparedField createServiceField(Class<?> asyncClass) throws Exception {
    String fieldName = "serviceAsync_" + asyncClass.getSimpleName() + "_" + rndId();
    
    PrintBlock p = content.newPrintBlock();
    p.pr("private final ");
    p.pr(content.imp(asyncClass)).pr(" ").pr(fieldName).pr(" = ");
    
    printAsyncValue(p, asyncClass);
    
    return new PreparedField(asyncClass, fieldName, null, null);
  }
  
  private void printAsyncValue(PrintBlock p, Class<?> asyncClass) throws Exception {
    Class<?> syncClass = calcSyncClass(asyncClass);
    
    {
      Class<?> syncBeanClass = findBeanClassFor(syncClass);
      Class<?> syncAsyncConverterBeanClass = findBeanClassFor(SyncAsyncConverter.class);
      if (syncBeanClass != null && syncAsyncConverterBeanClass != null) {
        
        PreparedField syncFld = prepareField(syncBeanClass);
        PreparedField convFld = prepareField(syncAsyncConverterBeanClass);
        GoingTypes tt = GoingTypes.extractFromSync(calcSyncClass(asyncClass));
        String toServer = asString(tt.toServer);
        String fromServer = asString(tt.fromServer);
        String asyncCallback = content.imp(AsyncCallback.class);
        String request = content.imp(Request.class);
        
        p.pr("new ").pr(content.imp(asyncClass)).prn("() {");
        p.moreIndent();
        p.prn("@Override");
        p.pr("public ").pr(request).pr(" invoke(").pr(toServer).pr(" toServer, ");
        p.pr(asyncCallback).pr("<").pr(fromServer).prn("> callback) {");
        p.moreIndent();
        p.pr("return ").pr(convFld.accept()).pr(".convertInvoking(toServer, callback, ");
        p.pr(syncFld.accept()).prn(");");
        p.lessIndent();
        p.prn("}");
        p.lessIndent();
        p.prn("};");
        
        return;
      }
      
    }
    
    {
      String syncName = content.imp(syncClass);
      String gwtName = content.imp(GWT.class);
      
      p.pr(gwtName).pr(".create(").pr(syncName).prn(".class);");
      
      return;
    }
  }
  
  private String asString(Type type) {
    if (type instanceof Class) return ((Class<?>)type).getName();
    if (type instanceof ParameterizedType) return ((ParameterizedType)type).toString();
    throw new IllegalArgumentException("Unknown value " + type);
  }
  
  private Class<?> findBeanClassFor(Class<?> fieldType) {
    if (fieldType == null) throw new NullPointerException();
    
    Class<?> ret = null;
    
    for (Class<?> bean : beanClassSet) {
      if (fieldType.isAssignableFrom(bean)) {
        if (ret != null) throw new MoreThenOneBeanClassIsAssignable(fieldType, bean, ret);
        ret = bean;
      }
    }
    
    return ret;
  }
  
  private PreparedField createBeanField(Class<?> fieldType, Class<?> beanClass) throws Exception {
    
    if (beanClass != fieldType) {
      PreparedField fld = prepareField(beanClass);
      return new PreparedField(fieldType, null, fld.beanClass, fld.getterFieldName);
    }
    
    String fieldName = "getter_" + beanClass.getSimpleName() + "_" + rndId();
    
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
    
    return new PreparedField(beanClass, null, beanClass, fieldName);
  }
  
  private void injectDependencies(PrintBlock pb, String var, Class<?> beanClass) throws Exception {
    for (Field field : beanClass.getFields()) {
      if (field.getType() == BeanGetter.class) {
        injectGetterField(pb, var, field);
      }
      if (InvokeServiceAsync.class.isAssignableFrom(field.getType())) {
        injectAsyncField(pb, var, field);
      }
    }
  }
  
  private void injectAsyncField(PrintBlock p, String var, Field field) throws Exception {
    p.pr(var).pr(".").pr(field.getName()).pr(" = ");
    printAsyncValue(p, field.getType());
  }
  
  private void injectGetterField(PrintBlock pb, String var, Field field) throws Exception {
    ParameterizedType type = (ParameterizedType)field.getGenericType();
    Class<?> inType = (Class<?>)type.getActualTypeArguments()[0];
    String beanGetterStr = content.imp(BeanGetter.class);
    String inTypeStr = content.imp(inType);
    pb.pr(var).pr(".").pr(field.getName()).pr(" = ");
    PreparedField fld = prepareField(inType);
    if (fld.needGetterTypeConvert()) {
      pb.pr("(").pr(beanGetterStr).pr("<").pr(inTypeStr).pr(">)(Object)");
    }
    pb.pr(fld.getterFieldName()).prn(";");
  }
  
}
