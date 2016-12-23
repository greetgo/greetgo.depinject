package kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans.core;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.util.UsingBeanFactory;

@Bean
public class UsingBeanFactoryRoom {

  public void takeOffJacket() {
    UsingBeanFactory.log.add("Take off jacket");
  }

  public BeanGetter<UsingBeanFactoryWindow> window;

  public void lookOutWindow() {
    window.get().lookOut();
  }

  public BeanGetter<UsingBeanFactoryComputer> computer;

  public void turnComputerOn() {
    computer.get().turnOn();
  }
}
