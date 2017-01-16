package kz.greetgo.depinject.gen2.test_beans021;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann021.class)
public class BeanReplacer021 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
