package kz.greetgo.depinject.gen2.test_beans020;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.ReplaceInstanceOf;
import kz.greetgo.depinject.core.replace.BeanReplacer;

@Bean
@ReplaceInstanceOf(Iface20.class)
public class BeanReplacer020 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
