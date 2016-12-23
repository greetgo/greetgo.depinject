package kz.greetgo.depinject.testng.complex_tests.SimpleLink.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.complex_tests.SimpleLink.SimpleLinkTest;

@Bean
public class SimpleLinkRoom {

  static {
    SimpleLinkTest.log.add("Load class SimpleLinkRoom");
  }

  public SimpleLinkRoom() {
    SimpleLinkTest.log.add("Create instance of SimpleLinkRoom");
  }

  public void takeJacketOff() {
    SimpleLinkTest.log.add("Take off jacket");
  }

  public BeanGetter<SimpleLinkWindow> simpleLinkWindow;
  
  public void lookOutTheWindow() {
    simpleLinkWindow.get().lookOut();
  }

  public BeanGetter<SimpleLinkComputer> simpleLinkComputer;
  
  public void turnComputerOn() {
    simpleLinkComputer.get().turnOn();
  }

}
