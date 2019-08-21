package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.afterInject_sync;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Alone {
  public String getValueWhileAfterInject() {
    return "Value while after inject";
  }
}
