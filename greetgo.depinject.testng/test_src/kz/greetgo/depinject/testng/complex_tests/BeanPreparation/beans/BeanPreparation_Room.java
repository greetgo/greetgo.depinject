package kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class BeanPreparation_Room {

  public BeanGetter<BeanPreparation_Window> window;

  public void lookOutWindow() {
    window.get().lookOut();
  }

  public BeanGetter<BeanPreparation_WindowImpl> windowImpl;

  public void lookOutWindowImpl() {
    windowImpl.get().lookOut();
  }

}
