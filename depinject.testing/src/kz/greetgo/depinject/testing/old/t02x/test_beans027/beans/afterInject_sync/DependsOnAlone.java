package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.afterInject_sync;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.HasAfterInject;

@Bean
public class DependsOnAlone implements HasAfterInject {

  public BeanGetter<Alone> alone;

  public String valueInitiatedWhileAfterInject;

  @Override
  public void afterInject() throws Exception {
    String aloneValue = alone.get().getValueWhileAfterInject();
    valueInitiatedWhileAfterInject = "DependsOnAlone value : " + aloneValue;
  }
}
