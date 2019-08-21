package kz.greetgo.depinject.testing.old.test_beans040;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

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
