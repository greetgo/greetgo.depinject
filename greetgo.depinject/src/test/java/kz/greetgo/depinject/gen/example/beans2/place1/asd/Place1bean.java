package kz.greetgo.depinject.gen.example.beans2.place1.asd;

import kz.greetgo.depinject.gen.example.WowBean;
import kz.greetgo.depinject.gen.example.beans2.place2.asd.Place2bean;
import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.BeanGetter;

@Bean(singleton = false)
public class Place1bean implements WowBean {
  
  public BeanGetter<Place2bean> asd;
  
  @Override
  public void printWow() {
    System.out.println(getClass() + " printed 'wow'");
    asd.get().boom();
  }
}
