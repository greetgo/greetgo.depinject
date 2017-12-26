package kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans;

import kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.UsingFactoryMethodTest;

public class UsingFactoryMethodWindow {
  private final String place;

  public UsingFactoryMethodWindow(String place) {
    this.place = place;
    UsingFactoryMethodTest.log.add("Create class FactoryLinkWindow");
  }

  public void lookOut() {
    UsingFactoryMethodTest.log.add("Look out window at " + place);
  }

}
