package kz.greetgo.depinject.testng.complex_tests.BeanReplacer.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacer.BeanReplacerAnn;

import static kz.greetgo.depinject.testng.complex_tests.BeanReplacer.BeanReplacerTest.log;

@Bean
@ReplaceWithAnn(BeanReplacerAnn.class)
public class BeanReplacer_WindowReplacer implements BeanReplacer {

  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {

    System.out.println("!!!!!!!!!! returnClass = " + returnClass);

    if (returnClass == BeanReplacer_Window.class) {
      return (BeanReplacer_Window) () -> {
        log.add("Before look out");
        ((BeanReplacer_Window) originalBean).lookOut();
        log.add("After look out");
      };
    }

    return originalBean;
  }
}
