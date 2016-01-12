package kz.greetgo.depinject.gwt.stand.src.somebeans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gwt.stand.remote.SomeGetServiceAsync;
import kz.greetgo.depinject.gwt.stand.src.AsdIface;

import com.google.gwt.user.client.Window;

@Bean
public class OneBean implements AsdIface {
  
  public SomeGetServiceAsync someGetService;
  
  public BeanGetter<TwoBean> two;
  
  @Override
  public void showHelloWorld() {
    Window.alert("someGetService = " + someGetService);
    Window.alert("two = " + two.get());
  }
}
