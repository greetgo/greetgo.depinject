package kz.greetgo.depinject.gen.t01x.test_beans018;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;
import kz.greetgo.depinject.core.replace.ReplacePriority;

@Bean
@ReplacePriority(5)
@ReplaceWithAnn(Ann018.class)
public class Replacer018_2 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
