package kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

import java.util.List;

@Bean
public class MainBean03302Impl implements MainBean03302 {
  private final BeanGetter<List<BeanGroup03302_1>> beans1;
  private final BeanGetter<List<BeanGroup03302_2>> beans2;

  public MainBean03302Impl(BeanGetter<List<BeanGroup03302_1>> beans1,
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
