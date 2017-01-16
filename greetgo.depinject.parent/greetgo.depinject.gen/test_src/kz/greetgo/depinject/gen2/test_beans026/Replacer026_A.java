package kz.greetgo.depinject.gen2.test_beans026;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplacePriority;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann026.class)
@ReplacePriority(10)
public class Replacer026_A implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
