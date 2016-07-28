package kz.greetgo.depinject.gen.beans.afterInject_sync;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;

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
