package kz.greetgo.depinject.testng.tests.p003_skip_inject_def_constructor.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.SkipInject;

import static org.fest.assertions.api.Assertions.assertThat;

@Bean
public class BeanTest003_main {
  @SuppressWarnings("unused")
  @SkipInject
  private BeanGetter<BeanTest003_ref> beanTest002_ref;

  public void assertRefNull() {
    assertThat(beanTest002_ref).isNull();
  }
}
