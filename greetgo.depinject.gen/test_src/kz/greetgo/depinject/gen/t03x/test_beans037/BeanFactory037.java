package kz.greetgo.depinject.gen.t03x.test_beans037;

import kz.greetgo.depinject.core.Bean;

@Bean
public class BeanFactory037 {

  @Bean(id = "bean1")
  public BeanTarget037 create1() {
    return new BeanTarget037() {};
  }

  @Bean(id = "bean2")
  public BeanTarget037 create2() {
    return new BeanTarget037() {};
  }

  @Bean(id = "bean3")
  public BeanTarget037 create3() {
    return new BeanTarget037() {};
  }

}
