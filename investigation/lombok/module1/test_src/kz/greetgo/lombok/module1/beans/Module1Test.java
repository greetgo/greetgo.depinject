package kz.greetgo.lombok.module1.beans;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigModule1.class)
public class Module1Test extends AbstractDepinjectTestNg {

  public BeanGetter<Module1> module1;

  @SuppressWarnings("SpellCheckingInspection")
  @Test
  public void test_working_with_lombok() {

    StringBuilder out = new StringBuilder();

    module1.get().print(out);

//    System.out.println(out);

    assertThat(out.toString()).contains("Hello from Module1");
    assertThat(out.toString()).contains("Hi from Module2");
    assertThat(out.toString()).contains("Priuvet from Module3");
    assertThat(out.toString()).contains("messageFromModule3 = Message from Module3");
    assertThat(out.toString()).contains("coolMessage  = Cool message from");
    assertThat(out.toString()).contains("superMessage = super message from Module2");
  }
}
