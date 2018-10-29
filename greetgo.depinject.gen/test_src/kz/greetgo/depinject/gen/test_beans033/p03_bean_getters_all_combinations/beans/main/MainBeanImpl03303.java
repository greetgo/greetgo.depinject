package kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.main;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group1.BeanGroup03303_1;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group2.BeanGroup03303_2;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.simple.SimpleBean03303_1;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.simple.SimpleBean03303_2;

import java.util.List;

@Bean
public class MainBeanImpl03303 implements MainBean03303 {

  public BeanGetter<List<BeanGroup03303_1>> beanGroup1;
  private final BeanGetter<List<BeanGroup03303_2>> beanGroup2;
  private final BeanGetter<SimpleBean03303_2> simpleBean2;

  public BeanGetter<SimpleBean03303_1> simpleBean1;

  public MainBeanImpl03303(BeanGetter<List<BeanGroup03303_2>> beanGroup2, BeanGetter<SimpleBean03303_2> simpleBean2) {
    this.beanGroup2 = beanGroup2;
    this.simpleBean2 = simpleBean2;
  }

  @Override
  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
    beanGroup1.get().forEach(b -> b.hello1(out));
    beanGroup2.get().forEach(b -> b.hello2(out));

    simpleBean1.get().hi(out);
    simpleBean2.get().hi(out);
  }
}
