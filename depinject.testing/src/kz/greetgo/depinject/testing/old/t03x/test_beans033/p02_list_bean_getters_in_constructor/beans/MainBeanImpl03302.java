package kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;

import java.beans.ConstructorProperties;
import java.util.List;

@Bean
public class MainBeanImpl03302 implements MainBean03302 {
  private final BeanGetter<List<BeanGroup03302_1>> beans1;
  private final BeanGetter<List<BeanGroup03302_2>> beans2;

  @ConstructorProperties({"beans1", "beans2"})
  public MainBeanImpl03302(BeanGetter<List<BeanGroup03302_1>> beans1,
                           BeanGetter<List<BeanGroup03302_2>> beans2) {
    this.beans1 = beans1;
    this.beans2 = beans2;
  }

  @Override
  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
    beans1.get().forEach(b -> b.hello(out));
    beans2.get().forEach(b -> b.hi(out));
  }
}
