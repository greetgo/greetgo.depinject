package kz.greetgo.depinject.testing.old.t01x.test_beans018;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanReplacer;
import kz.greetgo.depinject.ann.ReplaceInstanceOf;
import kz.greetgo.depinject.ann.ReplacePriority;

@Bean
@ReplacePriority(10)
@ReplaceInstanceOf(Iface018.class)
public class Replacer018_1 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
