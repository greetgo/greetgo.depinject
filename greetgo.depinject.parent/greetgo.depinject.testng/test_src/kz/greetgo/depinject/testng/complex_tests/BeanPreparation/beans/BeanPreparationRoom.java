package kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class BeanPreparationRoom {

  public BeanGetter<BeanPreparationWindow> window;

  public void lookOutWindow() {
    window.get().lookOut();
  }

  public BeanGetter<BeanPreparationWindowImpl> windowImpl;

  public void lookOutWindowImpl() {
    windowImpl.get().lookOut();
  }

}
