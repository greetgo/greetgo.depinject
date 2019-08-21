package kz.greetgo.depinject.testing.old.t01x.test_beans011;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.FactoredBy;

@Bean
@FactoredBy(BeanA3_beanFactory.class)
public interface BeanA3 {}
