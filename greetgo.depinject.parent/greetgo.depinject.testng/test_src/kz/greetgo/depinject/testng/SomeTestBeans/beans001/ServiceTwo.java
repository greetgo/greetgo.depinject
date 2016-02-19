package kz.greetgo.depinject.testng.SomeTestBeans.beans001;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.QwertyService;

@Bean
public class ServiceTwo implements QwertyService {
  @Override
  public void hi() {
    System.out.println("Hi from service two");
  }
}
