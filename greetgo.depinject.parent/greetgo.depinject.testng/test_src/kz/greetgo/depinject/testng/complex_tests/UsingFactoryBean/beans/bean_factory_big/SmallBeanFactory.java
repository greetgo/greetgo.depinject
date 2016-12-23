package kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans.bean_factory_big;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans.core.LocalBeanFactory;

@Bean
public class SmallBeanFactory extends LocalBeanFactory {

  private int windowNumber = 1;

  @Override
  protected int nextWindowNumber() {
    return windowNumber++;
  }

  private int computerNumber = 1;

  @Override
  protected int nextComputerNumber() {
    return computerNumber++;
  }
}