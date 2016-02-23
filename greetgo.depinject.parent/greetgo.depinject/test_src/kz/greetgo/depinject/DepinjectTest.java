package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;
import org.testng.annotations.Test;

public class DepinjectTest {

  class TestBeanContainer implements BeanContainer {
  }

  @Test(expectedExceptions = ClassNotFoundException.class)
  public void newInstance_ClassNotFoundException() throws Exception {
    Depinject.newInstance(TestBeanContainer.class);
  }
}