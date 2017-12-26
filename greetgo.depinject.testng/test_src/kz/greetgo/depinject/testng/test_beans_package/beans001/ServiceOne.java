package kz.greetgo.depinject.testng.test_beans_package.beans001;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.test_beans_package.beans002.QwertyService;

@Bean
public class ServiceOne implements QwertyService {
  @Override
  public void hi() {
    System.out.println("Hi from service one");
  }
}
