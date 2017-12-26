package kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans;

import kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.UsingFactoryMethodTest;

public class UsingFactoryMethodComputer {
  private final int number;

  public UsingFactoryMethodComputer(int number) {
    this.number = number;
    UsingFactoryMethodTest.log.add("Create class FactoryLinkComputer");
  }

  public void turnOn() {
    UsingFactoryMethodTest.log.add("Turn on computer " + number);
  }
}
