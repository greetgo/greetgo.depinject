package kz.greetgo.depinject.gen.test_beans025;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

@Bean
@Ann025
@ReplaceWithAnn(Ann025.class)
public class Replacer025_more implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
