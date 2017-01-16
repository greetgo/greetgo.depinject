package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.BeanReference;
import kz.greetgo.depinject.gen.Utils;

public class NoCandidates extends RuntimeException {
  public final BeanReference beanReference;

  public NoCandidates(BeanReference beanReference) {
    super(Utils.asStr(beanReference.sourceClass) + " in " + beanReference.place);
    this.beanReference = beanReference;
  }
}
