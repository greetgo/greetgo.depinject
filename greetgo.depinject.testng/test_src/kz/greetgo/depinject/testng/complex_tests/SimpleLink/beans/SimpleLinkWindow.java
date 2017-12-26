package kz.greetgo.depinject.testng.complex_tests.SimpleLink.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.SimpleLink.SimpleLinkTest;

@Bean
public class SimpleLinkWindow {

  static {
    SimpleLinkTest.log.add("Loading class SimpleLinkWindow");
  }

  public SimpleLinkWindow() {
    SimpleLinkTest.log.add("Creating class SimpleLinkWindow");
  }

  public void lookOut() {
    SimpleLinkTest.log.add("Looking out window");
  }
}
