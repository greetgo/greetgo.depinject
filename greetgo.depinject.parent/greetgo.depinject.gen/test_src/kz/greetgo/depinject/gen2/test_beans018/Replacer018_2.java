package kz.greetgo.depinject.gen2.test_beans018;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.BeanHasDeclaredAnnotation;
import kz.greetgo.depinject.core.replace.ReplacePriority;

@Bean
@ReplacePriority(5)
@BeanHasDeclaredAnnotation(Ann018.class)
public class Replacer018_2 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
