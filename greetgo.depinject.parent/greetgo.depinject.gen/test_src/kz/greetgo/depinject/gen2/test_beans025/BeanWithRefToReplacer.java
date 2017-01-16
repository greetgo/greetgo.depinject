package kz.greetgo.depinject.gen2.test_beans025;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class BeanWithRefToReplacer {
  @SuppressWarnings("unused")
  public BeanGetter<Replacer025> replacer025;

  @SuppressWarnings("unused")
  public BeanGetter<Replacer025_more> replacer025_more;
}
