package kz.greetgo.depinject.gen.t04x.test_beans040;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

import java.beans.ConstructorProperties;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Bean
public class MainBean040 extends MainBean040_parent {

  @Qualifier("bean3")
  private final BeanGetter<Bean040> bean3_reference;

  @ConstructorProperties({"bean1_reference", "bean2_reference", "bean3_reference"})
  public MainBean040(BeanGetter<Bean040> bean1_reference,
                     BeanGetter<Bean040> bean2_reference,
                     BeanGetter<Bean040> bean3_reference) {
    super(bean1_reference, bean2_reference);
    this.bean3_reference = bean3_reference;
  }
}
