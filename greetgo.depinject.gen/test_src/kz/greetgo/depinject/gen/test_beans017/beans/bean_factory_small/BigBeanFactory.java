package kz.greetgo.depinject.gen.test_beans017.beans.bean_factory_small;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.gen.test_beans017.beans.core.LocalBeanFactory;

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