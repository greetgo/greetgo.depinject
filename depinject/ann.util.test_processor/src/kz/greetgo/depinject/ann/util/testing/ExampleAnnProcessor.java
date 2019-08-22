package kz.greetgo.depinject.ann.util.testing;

import kz.greetgo.depinject.ann.util.AnnProcUtil;
import kz.greetgo.depinject.ann.util.BeanRefData;
import kz.greetgo.depinject.ann.util.PlaceUtil;
import kz.greetgo.depinject.ann.util.ValueEntry;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static kz.greetgo.depinject.ann.util.AnnProcUtil.getMethodAnnotationQualifiedNames;
import static kz.greetgo.depinject.ann.util.AnnProcUtil.getMethodAnnotationValues;
import static org.fest.assertions.api.Assertions.assertThat;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ExampleAnnProcessor extends AbstractProcessor {

  private static final String TEST_RESULTS_PACKAGE = "kz.greetgo.depinject.ann.util.testing";
  private static final String TEST_RESULTS_NAME = "TestResultsImpl";
  private static final String TEST_RESULTS = TEST_RESULTS_PACKAGE + '.' + TEST_RESULTS_NAME;

  boolean processed = false;

  public static TestResults newTestResults() {
    try {
      return (TestResults) Class.forName(TEST_RESULTS).getDeclaredConstructor().newInstance();
    } catch (
      InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException
        | ClassNotFoundException
        exception
    ) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    if (!processed) {
      processed = true;
      try {
        doProcess(annotations, roundEnv);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return false;
  }

  public static final String HI_WORLD = "HI WORLD!!!";

  private static ExecutableElement findMethod(String methodName, List<ExecutableElement> methods) {
    for (ExecutableElement method : methods) {
      if (methodName.equals(method.getSimpleName().toString())) {
        return method;
      }
    }
    throw new RuntimeException("Cannot find method with name = `" + methodName + "`");
  }

  private void doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws Exception {

    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(TEST_RESULTS);
    try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {

      out.println("package " + TEST_RESULTS_PACKAGE + ";");
      out.println("public class " + TEST_RESULTS_NAME + " implements " + TestResults.class.getName() + " {");
      out.println("  public String hello() {");
      out.println("    return \"" + HI_WORLD + "\";");
      out.println("  }");


      for (Element rootElement : roundEnv.getRootElements()) {
        if (rootElement instanceof TypeElement) {
          TypeElement typeElement = (TypeElement) rootElement;
          if ("kz.greetgo.depinject.ann.util.testing.SomeClassX".equals(typeElement.getQualifiedName().toString())) {

            List<ExecutableElement> methods = AnnProcUtil.extractMethods(typeElement);

            {
              ExecutableElement method = findMethod("someMethodX", methods);
              printReadAnnotationsFromSomeMethodX(out, method);
            }

            {
              ExecutableElement method = findMethod("returnsListSomeModel1", methods);
              print_returns_toCode(out, "returnsListSomeModel1", method);
            }
            {
              ExecutableElement method = findMethod("returnsListSomeModel2", methods);
              print_returns_toCode(out, "returnsListSomeModel2", method);
            }

            {
              ExecutableElement method = findMethod("returnsSomeModel1", methods);
              print_returns_toCode(out, "returnsSomeModel1", method);
            }
            {
              ExecutableElement method = findMethod("returnsSomeModel2", methods);
              print_returns_toCode(out, "returnsSomeModel2", method);
            }

            testToBeanRefData(methods);

          }

        }
      }


      out.println("}");

    }

  }

  private static void testToBeanRefData(List<ExecutableElement> methods) {

    {
      ExecutableElement method = findMethod("returnsListSomeModel1", methods);
      BeanRefData beanRefData = AnnProcUtil.toBeanRefData(method.getReturnType(), PlaceUtil.NO_WHERE);

      assertThat(beanRefData).isNotNull();
      assertThat(beanRefData.isList).isTrue();
      assertThat(beanRefData.typeElement.toString()).isEqualTo("kz.greetgo.depinject.ann.util.testing.model.SomeModel1");
    }
    {
      ExecutableElement method = findMethod("returnsSomeModel1", methods);
      BeanRefData beanRefData = AnnProcUtil.toBeanRefData(method.getReturnType(), PlaceUtil.NO_WHERE);
      assertThat(beanRefData).isNotNull();
      assertThat(beanRefData.isList).isFalse();
      assertThat(beanRefData.typeElement.toString()).isEqualTo("kz.greetgo.depinject.ann.util.testing.model.SomeModel1");
    }

  }

  private static void print_returns_toCode(PrintWriter out, String methodName, ExecutableElement method) {
    out.println("  public String " + methodName + "_toCode() {");
    out.println("    return \"" + AnnProcUtil.toCode(method.getReturnType()) + "\";");
    out.println("  }");
  }

  private static void printReadAnnotationsFromSomeMethodX(PrintWriter out, ExecutableElement method) {

    out.println("  public java.util.Map readAnnotationsFromSomeMethodX() {");
    out.println("    java.util.HashMap ret = new java.util.HashMap();");

    List<String> methodAnnotationQualifiedNames = getMethodAnnotationQualifiedNames(method);
    for (String qualifiedName : methodAnnotationQualifiedNames) {
      out.println("    {");
      out.println("      java.util.HashMap kv = new java.util.HashMap();");

      Map<String, ValueEntry> map = getMethodAnnotationValues(method, qualifiedName);
      //noinspection CodeBlock2Expr
      map.entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry::getKey))
        .forEachOrdered(e -> {
          out.println("      kv.put(\"" + e.getKey() + "\", \"" + e.getValue() + "\");");
        });
      out.println("      ret.put(\"" + qualifiedName + "\", kv);");
      out.println("    }");
    }

    out.println("    return ret;");
    out.println("  }");

  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> ret = new HashSet<>();
    ret.add(ExampleAnnotation.class.getName());
    return ret;
  }

}
