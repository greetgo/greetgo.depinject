package kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.bean_factory_small;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.core.LocalBeanFactory;

@Bean
public class BigBeanFactory extends LocalBeanFactory {
  private int windowNumber = 1000;

  @Override
  protected int nextWindowNumber() {
    return ++windowNumber;
  }

  private int computerNumber = 1000;

  @Override
  protected int nextComputerNumber() {
    return ++computerNumber;
  }
}
