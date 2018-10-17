package kz.greetgo.depinject.gen.test_beans033;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class MainBean033 {
  private final BeanGetter<Bean033_01> bean033_01;
  private final BeanGetter<Bean033_02> bean033_02;
  private final BeanGetter<Bean033_03> bean033_03;

  public MainBean033(BeanGetter<Bean033_01> bean033_01,
                     BeanGetter<Bean033_02> bean033_02,
                     BeanGetter<Bean033_03> bean033_03) {
    this.bean033_01 = bean033_01;
    this.bean033_02 = bean033_02;
    this.bean033_03 = bean033_03;
  }

  public void hello() {
    System.out.println("Hello from " + getClass().getSimpleName());
    bean033_01.get().hello();
    bean033_02.get().hello();
    bean033_03.get().hello();
  }
}
