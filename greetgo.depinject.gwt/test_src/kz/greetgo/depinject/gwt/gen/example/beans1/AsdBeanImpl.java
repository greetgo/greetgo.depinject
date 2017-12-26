package kz.greetgo.depinject.gwt.gen.example.beans1;

import kz.greetgo.depinject.gwt.gen.example.AsdBean;
import kz.greetgo.depinject.gwt.gen.example.WowBean;
import kz.greetgo.depinject.gwt.gen.example.remote.FakedServiceAsync;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

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
