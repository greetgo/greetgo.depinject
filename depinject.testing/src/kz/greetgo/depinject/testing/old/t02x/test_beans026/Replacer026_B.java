package kz.greetgo.depinject.testing.old.t02x.test_beans026;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplacePriority;
import kz.greetgo.depinject.ann.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann026.class)
@ReplacePriority(5)
public class Replacer026_B implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
