package kz.greetgo.depinject.gen2.UsingBeanFactory.beans.bean_factory_small;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.gen2.UsingBeanFactory.beans.core.LocalBeanFactory;

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