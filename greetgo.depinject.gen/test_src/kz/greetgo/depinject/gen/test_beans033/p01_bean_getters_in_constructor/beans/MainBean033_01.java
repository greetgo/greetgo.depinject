package kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class MainBean033_01 {
  private final BeanGetter<Bean033_01_1> bean033_01;
  private final BeanGetter<Bean033_01_2> bean033_02;
  private final BeanGetter<Bean033_01_3> bean033_03;

  public MainBean033_01(BeanGetter<Bean033_01_1> bean033_01,
                        BeanGetter<Bean033_01_2> bean033_02,
                        BeanGetter<Bean033_01_3> bean033_03) {
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
