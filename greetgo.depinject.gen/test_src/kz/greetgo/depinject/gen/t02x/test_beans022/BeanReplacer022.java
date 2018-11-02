package kz.greetgo.depinject.gen.t02x.test_beans022;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann022.class)
public class BeanReplacer022 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    return originalBean;
  }
}
