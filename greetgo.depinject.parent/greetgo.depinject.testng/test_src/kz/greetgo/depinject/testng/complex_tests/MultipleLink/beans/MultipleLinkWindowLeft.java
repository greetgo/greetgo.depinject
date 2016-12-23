package kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class MultipleLinkWindowLeft implements MultipleLinkWindow {
  @Override
  public String name() {
    return "Left";
  }
  
}
