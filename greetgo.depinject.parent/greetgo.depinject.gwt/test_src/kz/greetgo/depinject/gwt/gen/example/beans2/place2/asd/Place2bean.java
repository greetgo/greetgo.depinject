package kz.greetgo.depinject.gwt.gen.example.beans2.place2.asd;

import kz.greetgo.depinject.gwt.gen.example.remote.DirectServiceAsync;
import kz.greetgo.depinject.gwt.gen.example.remote.FakedServiceAsync;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;

@Bean
public class Place2bean implements HasAfterInject {
  
  public FakedServiceAsync fakedService;
  
  public DirectServiceAsync directService;
  
  public BeanGetter<FakedServiceAsync> fakedServiceGetter;
  
  public BeanGetter<DirectServiceAsync> directServiceGetter;
  
  public void boom() {
    System.out.println("boom");
  }
  
  @Override
  public void afterInject() throws Exception {
    System.out.println("After inject in " + getClass());
    System.out.println("fakedService = " + fakedService);
    System.out.println("directService = " + directService);
  }
}
