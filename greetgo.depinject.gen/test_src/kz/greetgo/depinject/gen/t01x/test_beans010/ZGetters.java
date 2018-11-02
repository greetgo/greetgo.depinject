package kz.greetgo.depinject.gen.t01x.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

import java.util.List;

@Bean
public class ZGetters {

  public BeanGetter<List<BeanA1>> beanA1;

  public BeanGetter<List<BeanA1_impl>> beanA1_impl;

  public BeanGetter<List<BeanA2>> beanA2;

  public BeanGetter<List<BeanA3>> beanA3;

  public BeanGetter<BeanA4> beanA4;
}
