package kz.greetgo.depinject.testing.old.test_beans040;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

import java.beans.ConstructorProperties;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public abstract class MainBean040_parent extends MainBean040_parent_parent {

  @Qualifier("bean2")
  private final BeanGetter<Bean040> bean2_reference;

  @ConstructorProperties({"bean1_reference", "bean2_reference"})
  public MainBean040_parent(BeanGetter<Bean040> bean1_reference,
                            BeanGetter<Bean040> bean2_reference) {
    super(bean1_reference);
    this.bean2_reference = bean2_reference;
  }
}
