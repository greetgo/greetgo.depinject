package kz.greetgo.depinject.testng.SomeTestBeans.beans002;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.SomeTestBeans.beans001.Picador;

@Bean
public class Cosines {
  public BeanGetter<Picador> picador;

  public BeanGetter<Sinus> sinus;

  public String hello() {
    return "Cosines says 'Hello'. "+picador.get().hello();
  }

  public String goodBy() {
    return "Cosines byes. "+sinus.get().goodBy();
  }
}
