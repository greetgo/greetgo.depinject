package kz.greetgo.depinject.testing.old.t02x.test_beans020;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplaceInstanceOf;

@Bean
@ReplaceInstanceOf(Iface20.class)
public class BeanReplacer020 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
