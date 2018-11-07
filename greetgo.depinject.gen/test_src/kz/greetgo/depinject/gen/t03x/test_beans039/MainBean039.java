package kz.greetgo.depinject.gen.t03x.test_beans039;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

import java.beans.ConstructorProperties;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Bean
public class MainBean039 {

  @Qualifier("bean1")
  private final BeanGetter<Bean039> bean1_reference;

  @Qualifier("bean2")
  private final BeanGetter<Bean039> bean2_reference;

  @Qualifier("bean3")
  private final BeanGetter<Bean039> bean3_reference;

  @ConstructorProperties({"bean1_reference", "bean2_reference", "bean3_reference"})
  public MainBean039(BeanGetter<Bean039> bean1_reference,
                     BeanGetter<Bean039> bean2_reference,
                     BeanGetter<Bean039> bean3_reference) {
    this.bean1_reference = bean1_reference;
    this.bean2_reference = bean2_reference;
    this.bean3_reference = bean3_reference;
  }
}
