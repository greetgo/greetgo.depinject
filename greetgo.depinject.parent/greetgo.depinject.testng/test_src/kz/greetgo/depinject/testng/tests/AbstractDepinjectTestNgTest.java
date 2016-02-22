package kz.greetgo.depinject.testng.tests;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.test_beans_package.AbstractDepinjectTestNgTestBeans;
import kz.greetgo.depinject.testng.test_beans_package.beans001.Kampala;
import kz.greetgo.depinject.testng.test_beans_package.beans001.Picador;
import kz.greetgo.depinject.testng.test_beans_package.beans002.Cosines;
import kz.greetgo.depinject.testng.test_beans_package.beans002.QwertyServiceStorage;
import kz.greetgo.depinject.testng.test_beans_package.beans002.Sinus;
import kz.greetgo.depinject.testng.test_beans_package.beans002.dogs.Nadia;
import kz.greetgo.depinject.testng.test_beans_package.beans002.dogs.Sharia;
import kz.greetgo.depinject.testng.test_beans_package.beans002.dogs.Sophia;
import kz.greetgo.depinject.testng.test_beans_package.for_include_by_str.OnSideBean;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(AbstractDepinjectTestNgTestBeans.class)
public class AbstractDepinjectTestNgTest extends AbstractDepinjectTestNg {

  public BeanGetter<Sinus> sinus;

  public BeanGetter<Kampala> kampala;

  @Override
  protected String getSrcTempDir() {
    return "build";
  }

  @Override
  protected boolean needToRemoveSrcDir() {
    return false;
  }

  @Test
  public void testHello() {
    System.out.println("From sinus: " + sinus.get().hello());
  }

  @Test
  public void testGoodBy() {
    System.out.println("From kampala: " + kampala.get().goodBy());
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

  public BeanGetter<QwertyServiceStorage> qwertyServiceStorage;

  @Test
  public void beanPreparation() {
    qwertyServiceStorage.get().hiAll();
  }

  public BeanGetter<OnSideBean> onSideBean;

  @Test
  public void onSideBeanTest() {
    assertThat(onSideBean).isNotNull();
    assertThat(onSideBean.get().hi("asd")).isEqualTo("Hi asd");
  }
}
