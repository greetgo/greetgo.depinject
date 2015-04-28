package kz.greetgo.depinject.stand.src.somebeans;

import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.stand.src.AsdIface;

import com.google.gwt.user.client.Window;

@Bean
public class OneBean implements AsdIface {
  
  @Override
  public void showHelloWorld() {
    Window.alert("Hello world from " + getClass());
  }
}
