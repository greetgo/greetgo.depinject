package kz.greetgo.depinject.gen2.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;

import java.util.List;

@Bean
public class BeanBSimple implements HasAfterInject {

  public BeanGetter<List<BeanA1>> beanA1;

  @Override
  public void afterInject() throws Exception {
    beanA1.get().forEach(BeanA1::asd);
  }
}
