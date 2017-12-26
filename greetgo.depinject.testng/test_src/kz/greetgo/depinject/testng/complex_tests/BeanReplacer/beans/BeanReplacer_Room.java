package kz.greetgo.depinject.testng.complex_tests.BeanReplacer.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class BeanReplacer_Room {

  public BeanGetter<BeanReplacer_Window> window;

  public void lookOutWindow() {
    window.get().lookOut();
  }

  public BeanGetter<BeanReplacer_WindowImpl> windowImpl;

  public void lookOutWindowImpl() {
    windowImpl.get().lookOut();
  }
}
