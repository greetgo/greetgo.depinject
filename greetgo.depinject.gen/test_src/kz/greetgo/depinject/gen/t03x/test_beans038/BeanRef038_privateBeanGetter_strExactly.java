package kz.greetgo.depinject.gen.t03x.test_beans038;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

import java.util.List;

@Bean
public class BeanRef038_privateBeanGetter_strExactly {

  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  @Qualifier("bean_hello_.*")
  private final BeanGetter<List<Bean038>> reference;

  public BeanRef038_privateBeanGetter_strExactly(BeanGetter<List<Bean038>> reference) {
    this.reference = reference;
  }
}
