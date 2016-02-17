package kz.greetgo.depinject.testng.SomeTestBeans.beans001;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.Cosines;

@Bean
public class Picador {

  public BeanGetter<Kampala> kampala;
  public String checkValue;

  public String hello() {
    return "Picador says 'Hello'. "+kampala.get().hello();
  }

  public BeanGetter<Cosines> cosines;

  public String goodBy() {
    return "Picador byes. "+cosines.get().goodBy();
  }

  public void acceptCheckValue(String checkValue) {
    this.checkValue=checkValue;
    kampala.get().acceptCheckValue(checkValue);
  }
}
