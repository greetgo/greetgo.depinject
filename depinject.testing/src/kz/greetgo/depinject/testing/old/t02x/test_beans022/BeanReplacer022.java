package kz.greetgo.depinject.testing.old.t02x.test_beans022;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann022.class)
public class BeanReplacer022 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
