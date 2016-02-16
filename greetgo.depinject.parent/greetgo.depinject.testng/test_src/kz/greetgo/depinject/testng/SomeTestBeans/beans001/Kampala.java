package kz.greetgo.depinject.testng.SomeTestBeans.beans001;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Kampala {

  public BeanGetter<Picador> picador;

  public String hello() {
    return "And Kampala says 'Hello'.";
  }

  public String goodBy() {
    return "Kampala byes. "+picador.get().goodBy();
  }
}
