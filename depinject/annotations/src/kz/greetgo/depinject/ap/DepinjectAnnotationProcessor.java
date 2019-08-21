package kz.greetgo.depinject.ap;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DepinjectAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    Set<? extends Element> annotatedWithBeanContainer = roundEnv.getElementsAnnotatedWith(BeanContainer.class);

    for (Element beanContainer : annotatedWithBeanContainer) {
      generateBeanContainer(beanContainer);
    }


    return false;
  }

  private void generateBeanContainer(Element beanContainer) {

    System.out.println("beanContainer = " + beanContainer);

    if (beanContainer instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) beanContainer;

      String qualifiedName = typeElement.getQualifiedName().toString();
      System.out.println("qualifiedName = " + qualifiedName);
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
