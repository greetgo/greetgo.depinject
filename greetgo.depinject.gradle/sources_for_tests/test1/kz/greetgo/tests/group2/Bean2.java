package kz.greetgo.tests.group2;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.tests.group1.Bean1;

public class Bean2 {

  public BeanGetter<Bean1> bean1;

  public void hello() {
    bean1.get().hello();
    System.out.println("Hello from bean2");
  }
}
