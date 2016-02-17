package kz.greetgo.depinject.testng.SomeTestBeans.beans002.dogs;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Sophia implements Dog {
  public String checkValue;

  @Override
  public void sayGave() {
    System.out.println("Sophia: gave gave gave");
  }

  @Override
  public void acceptCheckValue(String checkValue) {
    this.checkValue = checkValue;
  }
}
