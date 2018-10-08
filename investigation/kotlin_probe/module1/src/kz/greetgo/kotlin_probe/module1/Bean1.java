package kz.greetgo.kotlin_probe.module1;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.kotlin_probe.module2.Bean2;

@Bean
public class Bean1 {

  public BeanGetter<Bean2> bean2;

  public void printHelloWorld() {
    System.out.println("Hello World from " + getClass());
    bean2.get().printHelloWorld();
  }
}
