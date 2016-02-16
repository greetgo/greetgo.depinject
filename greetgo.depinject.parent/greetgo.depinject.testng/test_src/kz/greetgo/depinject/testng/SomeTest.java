package kz.greetgo.depinject.testng;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.SomeTestBeans.SomeTestBeans;
import kz.greetgo.depinject.testng.SomeTestBeans.beans001.Kampala;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.Sinus;
import org.testng.annotations.Test;

@ContainerConfig(SomeTestBeans.class)
public class SomeTest extends AbstractDepinjectTestNg {

  public BeanGetter<Sinus> sinus;

  public BeanGetter<Kampala> kampala;

  @Override
  protected String getSrcTempDir() {
    return "build";
  }

  @Test
  public void testHello() {
    System.out.println("from sinus: " + sinus.get().hello());
  }

  @Test
  public void testGoodBy() {
    System.out.println("from kampala: " + kampala.get().goodBy());
  }

}
