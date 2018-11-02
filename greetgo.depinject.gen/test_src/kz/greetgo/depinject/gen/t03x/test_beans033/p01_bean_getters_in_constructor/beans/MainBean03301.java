package kz.greetgo.depinject.gen.t03x.test_beans033.p01_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class MainBean03301 {
  private final BeanGetter<Bean03301_1> bean033_01;
  private final BeanGetter<Bean03301_2> bean033_02;
  private final BeanGetter<Bean03301_3> bean033_03;

  public MainBean03301(BeanGetter<Bean03301_1> bean033_01,
                       BeanGetter<Bean03301_2> bean033_02,
                       BeanGetter<Bean03301_3> bean033_03) {
    this.bean033_01 = bean033_01;
    this.bean033_02 = bean033_02;
    this.bean033_03 = bean033_03;
  }

  public void hello(StringBuilder out) {
    out.append("Hello from ").append(getClass().getSimpleName()).append("\n");
    bean033_01.get().hello(out);
    bean033_02.get().hello(out);
    bean033_03.get().hello(out);
  }
}
