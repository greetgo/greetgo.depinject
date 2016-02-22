package kz.greetgo.depinject.testng.test_beans_package.beans002.dogs;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Sharia implements Dog {
  public String checkValue;

  @Override
  public void sayGave() {
    System.out.println("Sharia: gave gave gave");
  }

  @Override
  public void acceptCheckValue(String checkValue) {
    this.checkValue = checkValue;
  }
}
