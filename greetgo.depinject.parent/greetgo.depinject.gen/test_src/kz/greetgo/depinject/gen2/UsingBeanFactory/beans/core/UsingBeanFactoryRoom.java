package kz.greetgo.depinject.gen2.UsingBeanFactory.beans.core;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen2.UsingBeanFactory.util.UsingBeanFactory;

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
