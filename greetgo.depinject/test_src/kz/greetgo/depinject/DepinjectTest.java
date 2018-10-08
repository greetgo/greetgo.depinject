package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;
import org.testng.annotations.Test;

public class DepinjectTest {

  class TestBeanContainer implements BeanContainer {}

  @Test(expectedExceptions = NoImplementor.class)
  public void newInstance_ClassNotFoundException() {
    Depinject.newInstance(TestBeanContainer.class);
  }
}
