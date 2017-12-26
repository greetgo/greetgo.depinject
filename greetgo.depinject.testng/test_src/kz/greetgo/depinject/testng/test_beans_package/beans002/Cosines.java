package kz.greetgo.depinject.testng.test_beans_package.beans002;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.test_beans_package.beans001.Picador;
import kz.greetgo.depinject.testng.test_beans_package.beans002.dogs.Dog;

import java.util.List;

@Bean
public class Cosines {
  public BeanGetter<Picador> picador;

  public BeanGetter<Sinus> sinus;
  public String checkValue;

  public String hello() {
    return "Cosines says 'Hello'. "+picador.get().hello();
  }

  public BeanGetter<List<Dog>> allDogs;

  public String goodBy() {

    for (Dog dog : allDogs.get()) {
      dog.sayGave();
    }

    return "Cosines byes. "+sinus.get().goodBy();
  }

  public void acceptCheckValue(String checkValue) {
    this.checkValue=checkValue;
    picador.get().acceptCheckValue(checkValue);

    for (Dog dog : allDogs.get()) {
      dog.acceptCheckValue(checkValue);
    }

  }
}
