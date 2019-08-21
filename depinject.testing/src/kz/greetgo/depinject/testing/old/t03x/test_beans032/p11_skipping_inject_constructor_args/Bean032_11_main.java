package kz.greetgo.depinject.testing.old.t03x.test_beans032.p11_skipping_inject_constructor_args;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.SkipInject;

import java.beans.ConstructorProperties;

@Bean
public class Bean032_11_main {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_11_1> bean032_09_1;
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private BeanGetter<Bean032_11_2> bean032_09_2;

  /**
   * This field MUST be private.
   */
  @SuppressWarnings("unused")
  @SkipInject
  private BeanGetter<Bean032_11_3> bean032_09_3;

  @SuppressWarnings("unused")
  @ConstructorProperties({"bean032_09_1", "bean032_09_2"})
  public Bean032_11_main(BeanGetter<Bean032_11_1> bean032_09_1, BeanGetter<Bean032_11_2> bean032_09_2) {
    this.bean032_09_1 = bean032_09_1;
    this.bean032_09_2 = bean032_09_2;
  }
}
