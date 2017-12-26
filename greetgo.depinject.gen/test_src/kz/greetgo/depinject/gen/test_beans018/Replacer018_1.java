package kz.greetgo.depinject.gen.test_beans018;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceInstanceOf;
import kz.greetgo.depinject.core.replace.ReplacePriority;

@Bean
@ReplacePriority(10)
@ReplaceInstanceOf(Iface018.class)
public class Replacer018_1 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
