package kz.greetgo.depinject.testng.SomeTestBeans.beans002;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

import java.util.List;

@Bean
public class QwertyServiceStorage {

  public BeanGetter<List<QwertyService>> allServices;

  public void hiAll() {
    for (QwertyService qwertyService : allServices.get()) {
      qwertyService.hi();
    }
  }
}
