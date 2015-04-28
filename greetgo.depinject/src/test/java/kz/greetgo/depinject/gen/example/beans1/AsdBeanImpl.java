package kz.greetgo.depinject.gen.example.beans1;

import kz.greetgo.depinject.gen.example.AsdBean;
import kz.greetgo.depinject.src.Bean;

@Bean
public class AsdBeanImpl implements AsdBean {
  @Override
  public void hello() {
    System.out.println("hello from " + getClass());
  }
}
