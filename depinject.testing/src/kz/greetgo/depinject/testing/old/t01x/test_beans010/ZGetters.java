package kz.greetgo.depinject.testing.old.t01x.test_beans010;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;

import java.util.List;

@Bean
public class ZGetters {

  public BeanGetter<List<BeanA1>> beanA1;

  public BeanGetter<List<BeanA1_impl>> beanA1_impl;

  public BeanGetter<List<BeanA2>> beanA2;

  public BeanGetter<List<BeanA3>> beanA3;

  public BeanGetter<BeanA4> beanA4;
}
