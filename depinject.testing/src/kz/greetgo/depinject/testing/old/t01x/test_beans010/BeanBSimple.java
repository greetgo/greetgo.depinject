package kz.greetgo.depinject.testing.old.t01x.test_beans010;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.HasAfterInject;

import java.util.List;

@Bean
public class BeanBSimple implements HasAfterInject {

  public BeanGetter<List<BeanA1>> beanA1;

  @Override
  public void afterInject() throws Exception {
    beanA1.get().forEach(BeanA1::asd);
  }
}
