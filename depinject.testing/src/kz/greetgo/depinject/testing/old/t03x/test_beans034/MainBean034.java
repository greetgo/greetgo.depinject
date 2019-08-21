package kz.greetgo.depinject.testing.old.t03x.test_beans034;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

@Bean
public class MainBean034 {
  @SuppressWarnings("unused")
  @Qualifier("Serenity")
  public BeanGetter<ReferenceToBean034> referenceToBean034;
}
