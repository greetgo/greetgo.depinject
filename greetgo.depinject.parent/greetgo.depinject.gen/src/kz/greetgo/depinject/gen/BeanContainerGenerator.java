package kz.greetgo.depinject.gen;

import kz.greetgo.class_scanner.ClassScanner;
import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.*;
import kz.greetgo.depinject.gen.errors.*;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static kz.greetgo.depinject.gen.BeanDefinition.findBeanDefinition;
import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;
import static kz.greetgo.util.ServerUtil.dummyCheck;

public class BeanContainerGenerator {

  private final static ClassScanner classScanner = new ClassScannerDef();

  public Class<?> beanContainerInterface;
  public String implClassName;
  public String packageName;

  public void writeToSourceDir(String sourceDir) {
    final String packagePath = packageName.replace('.', '/');
    File file = new File(sourceDir + '/' + packagePath + '/' + implClassName + ".java");
    dummyCheck(file.getParentFile().mkdirs());

    try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
      writeTo(writer);
    } catch (Exception e) {
      if (e instanceof RuntimeException) throw (RuntimeException) e;
      throw new RuntimeException(e);
    }
  }


  public static Map<Class<?>, BeanDefinition> collectBeanDefinitionsForBeanContainer(Class<?> beanContainerInterface) {

    if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
      throw new NoBeanContainer(beanContainerInterface);
    }

    final Include include = beanContainerInterface.getAnnotation(Include.class);
    if (include == null) throw new NoInclude(beanContainerInterface);

    {
      Map<Class<?>, BeanDefinition> ret = new HashMap<>();
      appendInclude(ret, include);
      return ret;
    }
  }

  private static void appendBeanDefinitions(Map<Class<?>, BeanDefinition> definitionMap, Class<?> beanConfigClass) {
    final BeanConfig beanConfig = beanConfigClass.getAnnotation(BeanConfig.class);
    if (beanConfig == null) throw new NoBeanConfig(beanConfigClass);

    final Include include = beanConfigClass.getAnnotation(Include.class);
    if (include != null) appendInclude(definitionMap, include);

    if (beanConfigClass.getAnnotation(BeanScanner.class) != null) {
      appendPackage(definitionMap, beanConfigClass.getPackage().getName());
    }

    {
      final BeanScannerPackage beanScannerPackage = beanConfigClass.getAnnotation(BeanScannerPackage.class);
      if (beanScannerPackage != null) {
        for (String packageName : beanScannerPackage.value()) {
          appendPackage(definitionMap, packageName);
        }
      }
    }
  }

  private static void appendInclude(Map<Class<?>, BeanDefinition> definitionMap, Include include) {
    for (Class<?> beanConfigClass : include.value()) {
      appendBeanDefinitions(definitionMap, beanConfigClass);
    }
  }

  private static void appendPackage(Map<Class<?>, BeanDefinition> definitionMap, String packageName) {
    for (Class<?> possibleBeanClass : classScanner.scanPackage(packageName)) {
      final Bean bean = possibleBeanClass.getAnnotation(Bean.class);
      if (bean == null) continue;
      createAndPutBeanAndItsFactoredBeans(definitionMap, possibleBeanClass, bean.singleton());
    }
  }

  private static void putBeanDefinition(Map<Class<?>, BeanDefinition> definitionMap, BeanDefinition beanDefinition) {
    final BeanDefinition existsBeanDefinition = definitionMap.get(beanDefinition.beanClass);
    if (existsBeanDefinition == null) {
      definitionMap.put(beanDefinition.beanClass, beanDefinition);
      return;
    }

    if (existsBeanDefinition.equals(beanDefinition)) return;

    throw new BeanAlreadyDefined(beanDefinition, existsBeanDefinition);
  }

  private static void createAndPutBeanAndItsFactoredBeans(Map<Class<?>, BeanDefinition> definitionMap,
                                                          Class<?> beanClass, boolean singleton) {
    putBeanDefinition(definitionMap, new BeanDefinition(beanClass, singleton, null, null));

    for (Method method : beanClass.getMethods()) {
      final Bean bean = method.getAnnotation(Bean.class);
      if (bean == null) continue;
      createAndPutFactoredBean(definitionMap, bean.singleton(), beanClass, method);
    }
  }

  private static void createAndPutFactoredBean(Map<Class<?>, BeanDefinition> definitionMap,
                                               boolean singleton,
                                               Class<?> factoryBeanClass,
                                               Method factoryMethod) {

    if (factoryMethod.getParameterTypes().length > 0) {
      throw new BeanFactoryMethodCannotHasAnyArguments(factoryMethod);
    }

    final Class<?> beanClass = factoryMethod.getReturnType();

    final BeanDefinition beanDefinition = new BeanDefinition(beanClass, singleton, factoryBeanClass, factoryMethod);

    putBeanDefinition(definitionMap, beanDefinition);
  }

  public static void initBeanDefinitions(Map<Class<?>, BeanDefinition> map) {
    initInjectors(map);
    initPreparations(map);
    initUsing(map);
    initHasAfterInject(map);
  }

  private static void initHasAfterInject(Map<Class<?>, BeanDefinition> map) {
    for (BeanDefinition beanDefinition : map.values()) {
      beanDefinition.hasAfterInject = HasAfterInject.class.isAssignableFrom(beanDefinition.beanClass);
    }
  }

  private static void initInjectors(Map<Class<?>, BeanDefinition> map) {
    for (BeanDefinition beanDefinition : map.values()) {
      beanDefinition.initInjectors(map);
    }
  }

  private static void initPreparations(Map<Class<?>, BeanDefinition> map) {
    for (BeanDefinition beanDefinition : map.values()) {
      beanDefinition.initFieldPrepareReferenceClass();
    }
    for (BeanDefinition beanDefinition : map.values()) {
      beanDefinition.initFieldPreparingBy(map);
    }
  }

  private static void initUsing(Map<Class<?>, BeanDefinition> map) {
    for (BeanDefinition beanDefinition : map.values()) {
      beanDefinition.initUsing(map);
    }
  }

  public static List<BeanContainerMethod> collectBeanContainerMethods(Class<?> beanContainerInterface,
                                                                      Map<Class<?>, BeanDefinition> map) {
    List<BeanContainerMethod> ret = new ArrayList<>();

    for (Method method : beanContainerInterface.getMethods()) {
      ret.add(createBeanContainerMethod(method, beanContainerInterface, map));
    }

    Collections.sort(ret);

    return ret;
  }

  private static BeanContainerMethod createBeanContainerMethod(Method method,
                                                               Class<?> beanContainerInterface,
                                                               Map<Class<?>, BeanDefinition> map) {

    if (method.getParameterTypes().length > 0) {
      throw new BeanContainerMethodCannotContainsAnyArguments(beanContainerInterface, method);
    }

    final Type referenceType = method.getGenericReturnType();
    final BeanDefinition beanDefinition = findBeanDefinition(referenceType, map, method.getDeclaringClass().toString());

    return new BeanContainerMethod(method.getName(), referenceType, beanDefinition);
  }

  private static void putBeanDefinitionRecursively(Map<Class<?>, BeanDefinition> map, BeanDefinition beanDefinition) {
    if (map.containsKey(beanDefinition.beanClass)) return;
    map.put(beanDefinition.beanClass, beanDefinition);

    for (BeanDefinition usingBeanDefinition : beanDefinition.using) {
      putBeanDefinitionRecursively(map, usingBeanDefinition);
    }

  }

  public static Map<Class<?>, BeanDefinition> collectUsedMap(List<BeanContainerMethod> methods) {
    Map<Class<?>, BeanDefinition> ret = new HashMap<>();

    for (BeanContainerMethod method : methods) {
      putBeanDefinitionRecursively(ret, method.beanDefinition);
    }

    return ret;
  }

  public void writeTo(PrintWriter writer) throws Exception {
    final Map<Class<?>, BeanDefinition> map = collectBeanDefinitionsForBeanContainer(beanContainerInterface);
    initBeanDefinitions(map);

    final List<BeanContainerMethod> methods = collectBeanContainerMethods(beanContainerInterface, map);

    final Map<Class<?>, BeanDefinition> usedMap = collectUsedMap(methods);

    List<BeanDefinition> beanDefinitionList = new ArrayList<>();
    beanDefinitionList.addAll(usedMap.values());
    Collections.sort(beanDefinitionList);
    {
      int nomer = 1;
      for (BeanDefinition beanDefinition : beanDefinitionList) {
        beanDefinition.getterName = "getter" + beanDefinition.beanClass.getSimpleName() + "_" + nomer++;
      }
    }

    writer.println("package " + packageName + ";");

    writer.println("public class " + implClassName + " implements " + toCode(beanContainerInterface) + " {");

    writer.println("");

    for (BeanContainerMethod method : methods) {
      writer.println("  @Override");
      writer.println("  public " + toCode(method.referenceType) + " " + method.name + "() {");
      writer.println("    return " + method.beanDefinition.getterName + ".get();");
      writer.println("  }");
      writer.println();
    }

    final String getterClass = BeanGetter.class.getName();

    for (BeanDefinition beanDefinition : beanDefinitionList) {
      String beanClass = beanDefinition.beanClass.getName();
      String concretGetterClass = getterClass + "<" + beanClass + ">";
      writer.println("  private final " + concretGetterClass + " " + beanDefinition.getterName
          + " = new " + concretGetterClass + "() {"
      );

      if (beanDefinition.singleton) {
        writer.println("    " + beanClass + " cachedValue = null;");
      }

      writer.println("    @Override");
      writer.println("    public " + beanClass + " get() {");

      if (beanDefinition.singleton) {
        writer.println("      if (cachedValue != null) return cachedValue;");
      }

      writer.println("      try {");

      writer.println("        " + beanClass + " localValue = " + beanDefinition.creationCode(usedMap) + ";");

      //writeBeforeInjectors(writer);

      for (Injector injector : beanDefinition.injectors) {
        injector.intoVariable(writer, "localValue", 8);
      }

      if (beanDefinition.hasAfterInject) {
        writer.println("        localValue.afterInject();");
      }

      //writeBeforePreparation(writer);

      for (BeanDefinition prepBD : beanDefinition.preparingBy) {
        writer.println("        localValue = (" + beanClass + ")" + prepBD.getterName + ".get().prepareBean(localValue);");
      }

      //writeBeforeReturn(writer);

      if (beanDefinition.singleton) {
        writer.println("        return cachedValue = localValue;");
      } else {
        writer.println("        return localValue;");
      }

      writer.println("      } catch(Exception e) {");
      writer.println("        throw new RuntimeException(e);");
      writer.println("      }");


      writer.println("    }");
      writer.println("  };");
      writer.println();
    }

    writer.println("}");
  }


}
