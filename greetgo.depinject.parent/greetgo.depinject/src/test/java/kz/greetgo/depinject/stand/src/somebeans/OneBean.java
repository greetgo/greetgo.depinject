package kz.greetgo.depinject.stand.src.somebeans;

import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.BeanGetter;
import kz.greetgo.depinject.stand.remote.SomeGetServiceAsync;
import kz.greetgo.depinject.stand.src.AsdIface;

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
