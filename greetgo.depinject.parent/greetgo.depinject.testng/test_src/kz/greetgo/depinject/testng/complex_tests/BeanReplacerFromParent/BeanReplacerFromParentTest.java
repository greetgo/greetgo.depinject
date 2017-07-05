package kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032.BeanConfig032;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032.Log032;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032.MainBean032;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfig032.class)
public class BeanReplacerFromParentTest extends AbstractDepinjectTestNg {

  public BeanGetter<MainBean032> mainBean032;

  @Test
  public void test() throws Exception {
    Log032.logs032.clear();

    mainBean032.get().hello(175);
    mainBean032.get().hello(317);

//    Log032.logs032.forEach(s -> System.out.println("LOG " + s));

    assertThat(Log032.logs032.get(0)).isEqualTo("Replacer032.replaceBeanClass ReplacingBean032Interface");
    assertThat(Log032.logs032.get(1)).isEqualTo("AbstractReplacer032 called method hello");
    assertThat(Log032.logs032.get(2)).isEqualTo("ReplacingBean032.hello 175");
    assertThat(Log032.logs032.get(3)).isEqualTo("AbstractReplacer032 called method hello");
    assertThat(Log032.logs032.get(4)).isEqualTo("ReplacingBean032.hello 317");

  }
}
