package kz.greetgo.depinject.ap;

import kz.greetgo.depinject.core.Bean;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class WowProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("WOW is processing : " + annotations + " --- " + roundEnv);


    try (PrintStream out = new PrintStream("/home/pompei/tmp/asd.txt", "UTF-8")) {

      out.println("WOW is processing : " + annotations + " --- " + roundEnv);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    return false;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> ret = new HashSet<>();
    ret.add(Bean.class.getName());
    return ret;
  }
}
