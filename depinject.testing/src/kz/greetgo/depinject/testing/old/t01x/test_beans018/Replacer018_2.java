package kz.greetgo.depinject.testing.old.t01x.test_beans018;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplacePriority;
import kz.greetgo.depinject.ann.ReplaceWithAnn;

@Bean
@ReplacePriority(5)
@ReplaceWithAnn(Ann018.class)
public class Replacer018_2 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
