package kz.greetgo.depinject.gen.t01x.test_beans017.beans.core;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.t01x.test_beans017.util.UsingBeanFactory;

@Bean
@SuppressWarnings("unused")
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
