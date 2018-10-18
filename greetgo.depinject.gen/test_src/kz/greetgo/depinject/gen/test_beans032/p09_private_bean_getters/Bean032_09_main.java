package kz.greetgo.depinject.gen.test_beans032.p09_private_bean_getters;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Bean032_09_main {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_09_1> bean032_09_1;
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_09_2> bean032_09_2;

  @SuppressWarnings("unused")
  public Bean032_09_main(BeanGetter<Bean032_09_1> bean032_09_1, BeanGetter<Bean032_09_2> bean032_09_2) {
    this.bean032_09_1 = bean032_09_1;
    this.bean032_09_2 = bean032_09_2;
  }
}
