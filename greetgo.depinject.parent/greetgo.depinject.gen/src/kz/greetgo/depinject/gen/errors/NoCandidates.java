package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.BeanConfigTree;
import kz.greetgo.depinject.gen.BeanReference;
import kz.greetgo.depinject.gen.Utils;

public class NoCandidates extends RuntimeException {
  public final BeanReference beanReference;
  public final BeanConfigTree configTree;

  public NoCandidates(BeanReference beanReference, BeanConfigTree configTree) {
    super(Utils.asStr(beanReference.sourceClass) + " in " + beanReference.place
      + configTree.asStr(false, true));
    this.beanReference = beanReference;
    this.configTree = configTree;
  }
}
