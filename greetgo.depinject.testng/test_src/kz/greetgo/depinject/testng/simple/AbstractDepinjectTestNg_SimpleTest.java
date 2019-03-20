package kz.greetgo.depinject.testng.simple;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.simple.beans.BeanConfigSimple;
import kz.greetgo.depinject.testng.simple.beans.SimpleBean;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigSimple.class)
public class AbstractDepinjectTestNg_SimpleTest extends AbstractDepinjectTestNg {

  public BeanGetter<SimpleBean> simpleBean;

  @Override
  protected boolean needToRemoveSrcDir() {
    return false;
  }

  @Override
  protected String generateSrcTempDir() {
    return "build/SimpleTest";
  }

  @Test
  public void testConnect() {

    //todo pompei runing test

    assertThat(simpleBean.get()).isNotNull();
  }

}
