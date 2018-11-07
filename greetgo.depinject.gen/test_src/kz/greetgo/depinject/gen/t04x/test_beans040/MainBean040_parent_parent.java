package kz.greetgo.depinject.gen.t04x.test_beans040;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;

import java.beans.ConstructorProperties;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public abstract class MainBean040_parent_parent {

  @Qualifier("bean1")
  private final BeanGetter<Bean040> bean1_reference;

  @ConstructorProperties({"bean1_reference"})
  public MainBean040_parent_parent(BeanGetter<Bean040> bean1_reference) {
    this.bean1_reference = bean1_reference;
  }
}
