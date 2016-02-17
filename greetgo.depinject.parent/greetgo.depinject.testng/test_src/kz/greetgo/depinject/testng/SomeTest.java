package kz.greetgo.depinject.testng;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.SomeTestBeans.SomeTestBeans;
import kz.greetgo.depinject.testng.SomeTestBeans.beans001.Kampala;
import kz.greetgo.depinject.testng.SomeTestBeans.beans001.Picador;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.Cosines;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.Sinus;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.dogs.Nadia;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.dogs.Sharia;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.dogs.Sophia;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(SomeTestBeans.class)
public class SomeTest extends AbstractDepinjectTestNg {

  public BeanGetter<Sinus> sinus;

  public BeanGetter<Kampala> kampala;

  @Override
  protected String getSrcTempDir() {
    return "build";
  }

  @Override
  protected boolean needToRemoveSrcDir() {
    return true;
  }

  @Test
  public void testHello() {
    System.out.println("from sinus: " + sinus.get().hello());
  }

  @Test
  public void testGoodBy() {
    System.out.println("from kampala: " + kampala.get().goodBy());
  }

  public BeanGetter<Cosines> cosines;
  public BeanGetter<Nadia> dogNadia;
  public BeanGetter<Sophia> dogSophia;
  public BeanGetter<Sharia> dogSharia;
  public BeanGetter<Picador> picador;

  @Test
  public void goToEveryWhere() {
    final String checkValue = RND.intStr(30);

    sinus.get().acceptCheckValue(checkValue);

    assertThat(sinus.get().checkValue).isEqualTo(checkValue);
    assertThat(cosines.get().checkValue).isEqualTo(checkValue);
    assertThat(dogNadia.get().checkValue).isEqualTo(checkValue);
    assertThat(dogSophia.get().checkValue).isEqualTo(checkValue);
    assertThat(dogSharia.get().checkValue).isEqualTo(checkValue);
    assertThat(picador.get().checkValue).isEqualTo(checkValue);
    assertThat(kampala.get().checkValue).isEqualTo(checkValue);
  }
}
