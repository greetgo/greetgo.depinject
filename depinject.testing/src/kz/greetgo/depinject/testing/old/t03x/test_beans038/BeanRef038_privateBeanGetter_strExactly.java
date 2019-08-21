package kz.greetgo.depinject.testing.old.t03x.test_beans038;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

import java.beans.ConstructorProperties;
import java.util.List;

@Bean
public class BeanRef038_privateBeanGetter_strExactly {

  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  @Qualifier("bean_hello_.*")
  private final BeanGetter<List<Bean038>> reference;

  @ConstructorProperties("reference")
  public BeanRef038_privateBeanGetter_strExactly(BeanGetter<List<Bean038>> reference) {
    this.reference = reference;
  }
}
