package kz.greetgo.lombok.module1.beans;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.lombok.module1.beans.fruits.FruitsAccess;
import kz.greetgo.lombok.module3.fruits.Fruit;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigModule1.class)
public class Module1Test extends AbstractDepinjectTestNg {

  public BeanGetter<Module1> module1;

  @Test
  @SuppressWarnings("SpellCheckingInspection")
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

  public BeanGetter<FruitsAccess> fruitsAccess;

  @Test
  public void checkAllRedFruits() {
    Set<String> fruits = fruitsAccess.get()
        .allRedFruits()
        .stream()
        .map(Fruit::name)
        .collect(Collectors.toSet());

    assertThat(fruits).containsOnly("red currant", "red apple");
  }

  @Test
  public void checkAllYellowFruits() {
    Set<String> fruits = fruitsAccess.get()
        .allYellowFruits()
        .stream()
        .map(Fruit::name)
        .collect(Collectors.toSet());

    System.out.println(fruits);

    assertThat(fruits).containsOnly("yellow banana", "yellow apple", "yellow lemon");
  }
}
