package kz.greetgo.depinject.testing.old.t03x.test_beans036.root;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;
import kz.greetgo.depinject.ann.SkipInject;

import java.beans.ConstructorProperties;

@Bean
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class BeanRef036_3 {

  @SkipInject
  private final BeanGetter<Object> leftRef1 = null;

  @Qualifier("in_constructor_arg_73746t356")
  private final BeanGetter<BeanTarget036_3> reference;

  @SkipInject
  private final BeanGetter<Object> leftRef2 = null;

  @ConstructorProperties("reference")
  public BeanRef036_3(@Qualifier("left_id") BeanGetter<BeanTarget036_3> reference) {
    this.reference = reference;
  }
}
