package kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;

@Bean
public class MultipleLinkWindowFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    if (beanClass == MultipleLinkWindowRight.class) return (MultipleLinkWindowRight) () -> "Right";
    throw new RuntimeException("Unknown " + beanClass);
  }

  public interface Center extends MultipleLinkWindow {
  }

  @Bean
  public Center createCenterWindow() {
    return () -> "Center";
  }

  public interface Top extends MultipleLinkWindow {
  }
  
  @Bean
  public Top createTopWindow() {
    return () -> "Top";
  }

}
