package kz.greetgo.depinject.testing.old.t02x.test_beans021;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann021.class)
public class BeanReplacer021 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
