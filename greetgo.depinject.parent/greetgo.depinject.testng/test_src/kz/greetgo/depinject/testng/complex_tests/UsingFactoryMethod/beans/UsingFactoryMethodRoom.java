package kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.UsingFactoryMethodTest;

@Bean
public class UsingFactoryMethodRoom {

  public void takeOffJacket() {
    UsingFactoryMethodTest.log.add("Take off jacket");
  }

  public BeanGetter<UsingFactoryMethodWindow> window;

  public void lookOutTheWindow() {
    window.get().lookOut();
  }

  public BeanGetter<UsingFactoryMethodComputer> computer;

  public void turnOnComputer() {
    computer.get().turnOn();
  }

}
