package kz.greetgo.depinject.ap;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.ap.engine.BeanContainerManager;
import kz.greetgo.depinject.ap.engine.ClassName;
import kz.greetgo.depinject.ap.engine.Context;
import kz.greetgo.depinject.ap.engine.OuterToStringBuilder;
import kz.greetgo.depinject.ap.message.DepinjectMessageLog;
import kz.greetgo.depinject.ann.util.message.Message;

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

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DepinjectAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    Set<? extends Element> annotatedWithBeanContainer = roundEnv.getElementsAnnotatedWith(BeanContainer.class);

    for (Element beanContainer : annotatedWithBeanContainer) {
      generateBeanContainer(beanContainer, roundEnv);
    }


    return false;
  }

  private void generateBeanContainer(Element beanContainer, RoundEnvironment roundEnv) {

    if (!(beanContainer instanceof TypeElement)) {
      return;
    }

    TypeElement typeElement = (TypeElement) beanContainer;

    if (!typeElement.getKind().isInterface()) {
      //TODO pompei надо сообщить предупреждение или ошибку
      return;
    }

    String qualifiedName = typeElement.getQualifiedName().toString();

    Context context = new Context(roundEnv, processingEnv);
    ClassName className = extractImplClassName(typeElement);

    BeanContainerManager bcm = context.createManager(typeElement);

    StringBuilder sb = new StringBuilder();
    OuterToStringBuilder outer = new OuterToStringBuilder("  ", sb);
    try {
      bcm.writeBeanContainerImpl(outer, className.packageName, className.name);
    } catch (Message message) {
      DepinjectMessageLog.appendMessage(qualifiedName, message);
      //noinspection ThrowablePrintedToSystemOut
      System.err.println(message);
    }

    if (DepinjectMessageLog.hasErrors(qualifiedName)) {
      DepinjectMessageLog.throwErrorIfNeed(qualifiedName);
    } else {

      try {
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(qualifiedName);

        try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {
          out.print(sb.toString());
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }


  }


  public static ClassName extractImplClassName(TypeElement typeElement) {
    //TODO pompei переделать это
    String qualifiedName = typeElement.getQualifiedName() + "Impl";
    return ClassName.parseQualifiedName(qualifiedName);
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
