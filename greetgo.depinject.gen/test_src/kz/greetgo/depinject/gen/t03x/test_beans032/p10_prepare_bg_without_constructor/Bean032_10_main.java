package kz.greetgo.depinject.gen.t03x.test_beans032.p10_prepare_bg_without_constructor;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Bean032_10_main {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_10_1> bean032_09_1;
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_10_2> bean032_09_2;

  @SuppressWarnings("unused")
  private BeanGetter<Bean032_10_3> bean032_09_3;

  @SuppressWarnings("unused")
  public Bean032_10_main(BeanGetter<Bean032_10_1> bean032_09_1, BeanGetter<Bean032_10_2> bean032_09_2) {
    this.bean032_09_1 = bean032_09_1;
    this.bean032_09_2 = bean032_09_2;
  }
}
