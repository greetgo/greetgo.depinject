package kz.greetgo.depinject.ap;

import com.sun.tools.javac.code.Symbol;
import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DepinjectAnnotationProcessorOld extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("WOW is processing : " + annotations + " --- " + roundEnv);

    //if (!roundEnv.processingOver()) {

//    Set<? extends Element> annotatedWithBean = roundEnv.getElementsAnnotatedWith(Bean.class);
//    System.out.println("annotatedWithBean = " + annotatedWithBean);

    Set<? extends Element> annotatedWithBeanContainer = roundEnv.getElementsAnnotatedWith(BeanContainer.class);
    System.out.println("annotatedWithBeanContainer = " + annotatedWithBeanContainer);

    for (Element beanContainer : annotatedWithBeanContainer) {
      generateBeanContainer(beanContainer);
    }


    //}

    return false;
  }

  private void generateBeanContainer(Element beanContainer) {
    if (beanContainer instanceof Symbol.ClassSymbol) {
      Symbol.ClassSymbol beanContainerClass = (Symbol.ClassSymbol) beanContainer;
      String implName = beanContainerClass.getQualifiedName() + "Impl";
      System.out.println("implName = " + implName);

      try {

        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(implName);

        try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {

          out.println("package kz.greetgo.depinject.testing.launcher;");
          out.println("public class ExampleContainerImpl implements kz.greetgo.depinject.testing.launcher.ExampleContainer {");
          out.println("  public kz.greetgo.depinject.testing.beans1.Hello1 hello1() {");
          out.println("    return new kz.greetgo.depinject.testing.beans1.Hello1();");
          out.println("  }");
          out.println("}");

        }


      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> ret = new HashSet<>();
    ret.add(Bean.class.getName());
    ret.add(Include.class.getName());
    ret.add(BeanContainer.class.getName());
    return ret;
  }
}
