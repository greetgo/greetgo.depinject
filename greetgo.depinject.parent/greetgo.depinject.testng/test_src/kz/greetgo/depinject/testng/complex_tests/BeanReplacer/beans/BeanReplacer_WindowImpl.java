package kz.greetgo.depinject.testng.complex_tests.BeanReplacer.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacer.BeanReplacerAnn;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacer.BeanReplacerTest;

import static kz.greetgo.depinject.testng.complex_tests.BeanReplacer.BeanReplacerTest.log;

@Bean
@BeanReplacerAnn
public class BeanReplacer_WindowImpl implements BeanReplacer_Window {
  @Override
  public void lookOut() {
    log.add("Look out window");
  }
}
