package kz.greetgo.depinject.gen2.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactoredBy;

@Bean
@BeanFactoredBy(BeanA3_beanFactory.class)
public interface BeanA3 extends BeanA2 {
}
