package kz.greetgo.depinject.gen.t03x.test_beans035;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

@Bean
public class MainBean035 {
  @SuppressWarnings("unused")
  @Qualifier("Serenity")
  public BeanGetter<ReferenceToBean035> referenceToBean;
}
