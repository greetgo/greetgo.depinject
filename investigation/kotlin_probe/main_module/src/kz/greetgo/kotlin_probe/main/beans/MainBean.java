package kz.greetgo.kotlin_probe.main.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.kotlin_probe.module1.Bean1;

@Bean
public class MainBean {

  public BeanGetter<Bean1> bean1;

  public void printHelloWorld() {
    System.out.println("Hello World from " + getClass().getSimpleName());
    bean1.get().printHelloWorld();
  }
}
