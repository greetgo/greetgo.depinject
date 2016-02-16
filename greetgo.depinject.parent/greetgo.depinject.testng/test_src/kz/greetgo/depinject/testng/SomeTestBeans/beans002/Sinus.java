package kz.greetgo.depinject.testng.SomeTestBeans.beans002;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Sinus {

  public BeanGetter<Cosines> cosines;

  public String hello() {
    return "Sinus says 'Hello'. " + cosines.get().hello();
  }

  public String goodBy() {
    return "Sinus byes.";
  }
}
