package kz.greetgo.depinject.gen.t03x.test_beans036.root;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

@Bean
public class BeanRef036_2 {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private final BeanGetter<BeanTarget036_2> ref;

  public BeanRef036_2(
      @Qualifier("in_constructor_arg_1672748") BeanGetter<BeanTarget036_2> ref
  ) {
    this.ref = ref;
  }
}
