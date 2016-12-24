package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static kz.greetgo.depinject.gen.BeanDefinition.findBeanDefinition;
import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;
import static kz.greetgo.util.ServerUtil.dummyCheck;

public class BeanContainerGenerator {

  public Class<?> beanContainerInterface;
  public String implClassName;
  public String packageName;

  public File writeToSourceDir(String sourceDir) {
    final String packagePath = packageName.replace('.', '/');
    File file = new File(sourceDir + '/' + packagePath + '/' + implClassName + ".java");
    dummyCheck(file.getParentFile().mkdirs());

    try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
      writeTo(writer);
    } catch (Exception e) {
      if (e instanceof RuntimeException) throw (RuntimeException) e;
      throw new RuntimeException(e);
    }

    return file;
  }

  public static Map<Class<?>, BeanDefinition> collectBeanDefinitionsForBeanContainer(Class<?> beanContainerInterface) {
    try {

      if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
        throw new NoBeanContainer(beanContainerInterface);
      }

      final Include include = beanContainerInterface.getAnnotation(Include.class);
      if (include == null) throw new NoInclude(beanContainerInterface);

      {
        BeanDefinitionCollector collector = new BeanDefinitionCollector();
        collector.appendInclude(include);
        collector.showDefinitionMap();
        return collector.getDefinitionMap();
      }

    } catch (Exception e) {
      if (e instanceof RuntimeException) throw (RuntimeException) e;
      throw new RuntimeException(e);
    }
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
      throw new BeanContainerMethodCannotContainAnyArguments(beanContainerInterface, method);
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
      int number = 1;
      for (BeanDefinition beanDefinition : beanDefinitionList) {
        beanDefinition.getterName = "getter" + beanDefinition.beanClass.getSimpleName() + "_" + number++;
      }
    }

    writer.println("package " + packageName + ";");

    writer.println("public class " + implClassName + " implements " + toCode(beanContainerInterface) + " {");

    writer.println("");
    writer.println("  private final java.lang.Object synchronizes = new java.lang.Object();");
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
      String beanClass = toCode(beanDefinition.beanClass);
      String concreteGetterClass = getterClass + "<" + beanClass + ">";
      writer.println("  private final " + concreteGetterClass + " " + beanDefinition.getterName
          + " = new " + concreteGetterClass + "() {"
      );

      if (beanDefinition.singleton) {
        writer.println("    java.util.concurrent.atomic.AtomicReference<" + beanClass + "> cachedValue" +
            " = new java.util.concurrent.atomic.AtomicReference<>(null);");
      }

      writer.println("    @Override");
      writer.println("    public " + beanClass + " get() {");

      if (beanDefinition.singleton) {
        writer.println("      {");
        writer.println("        " + beanClass + " x = cachedValue.get();");
        writer.println("        if (x != null) return x;");
        writer.println("      }");

        writer.println("      synchronized (synchronizes) {");
        writer.println("        {");
        writer.println("          " + beanClass + " x = cachedValue.get();");
        writer.println("          if (x != null) return x;");
        writer.println("        }");
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
        writer.println("        cachedValue.set(localValue);");
      }
      writer.println("        return localValue;");

      writer.println("      } catch(Exception e) {");
      writer.println("        throw new RuntimeException(e);");
      writer.println("      }");

      if (beanDefinition.singleton) {
        writer.println("      }");
      }


      writer.println("    }");
      writer.println("  };");
      writer.println();
    }

    writer.println("}");
  }


}
