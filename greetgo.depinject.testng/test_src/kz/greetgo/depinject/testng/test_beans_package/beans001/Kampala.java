package kz.greetgo.depinject.testng.test_beans_package.beans001;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Kampala {

  public BeanGetter<Picador> picador;
  public String checkValue;

  public String hello() {
    return "And Kampala says 'Hello'.";
  }

  public String goodBy() {
    return "Kampala byes. " + picador.get().goodBy();
  }

  public void acceptCheckValue(String checkValue) {
    this.checkValue = checkValue;

  }
}
