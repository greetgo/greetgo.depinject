package kz.greetgo.depinject.testing.old.t02x.test_beans025;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplaceWithAnn;

@Bean
@Ann025
@ReplaceWithAnn(Ann025.class)
public class Replacer025_more implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
