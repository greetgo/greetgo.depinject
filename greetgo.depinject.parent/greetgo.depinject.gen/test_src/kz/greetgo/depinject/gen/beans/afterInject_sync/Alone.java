package kz.greetgo.depinject.gen.beans.afterInject_sync;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Alone {
  public String getValueWhileAfterInject() {
    return "Value while after inject";
  }
}
