package kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Factories {

  int windowNumber = 1;

  @Bean
  public UsingFactoryMethodWindow createWindow() {
    return new UsingFactoryMethodWindow("number " + windowNumber++);
  }

  int computerNumber = 1;

  @Bean(singleton = false)
  public UsingFactoryMethodComputer createComputer() {
    return new UsingFactoryMethodComputer(computerNumber++);
  }

}
