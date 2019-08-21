package kz.greetgo.depinject.testing.old.t03x.test_beans036.root;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

import java.beans.ConstructorProperties;

@Bean
public class BeanRef036_2 {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private final BeanGetter<BeanTarget036_2> ref;

  @ConstructorProperties("ref")
  public BeanRef036_2(
      @Qualifier("in_constructor_arg_1672748") BeanGetter<BeanTarget036_2> ref
  ) {
    this.ref = ref;
  }
}
