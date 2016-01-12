package kz.greetgo.depinject.gen.example.beans1;

import kz.greetgo.depinject.gen.example.AsdBean;
import kz.greetgo.depinject.gen.example.WowBean;
import kz.greetgo.depinject.gen.example.remote.FakedServiceAsync;
import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.BeanGetter;

@Bean
public class AsdBeanImpl implements AsdBean {
  
  public BeanGetter<WowBean> wow;
  
  public FakedServiceAsync service;
  
  @Override
  public void hello() {
    System.out.println("hello from " + getClass());
    wow.get().printWow();
    System.out.println("service = " + service);
  }
}
