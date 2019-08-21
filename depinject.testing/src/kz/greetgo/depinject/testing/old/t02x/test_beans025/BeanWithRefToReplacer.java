package kz.greetgo.depinject.testing.old.t02x.test_beans025;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;

@Bean
public class BeanWithRefToReplacer {
  @SuppressWarnings("unused")
  public BeanGetter<Replacer025> replacer025;

  @SuppressWarnings("unused")
  public BeanGetter<Replacer025_more> replacer025_more;
}
