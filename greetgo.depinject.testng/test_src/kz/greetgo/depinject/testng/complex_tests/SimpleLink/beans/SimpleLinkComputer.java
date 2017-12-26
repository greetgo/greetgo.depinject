package kz.greetgo.depinject.testng.complex_tests.SimpleLink.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.SimpleLink.SimpleLinkTest;

@Bean(singleton = false)
public class SimpleLinkComputer {

  static {
    SimpleLinkTest.log.add("Load class SimpleLinkComputer");
  }

  public SimpleLinkComputer() {
    SimpleLinkTest.log.add("Create class SimpleLinkComputer");
  }

  public void turnOn() {
    SimpleLinkTest.log.add("Turn computer on");
  }

}
