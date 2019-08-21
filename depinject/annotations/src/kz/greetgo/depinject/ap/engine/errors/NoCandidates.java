package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.ap.engine.BeanConfigTree;
import kz.greetgo.depinject.ap.engine.BeanReference;
import kz.greetgo.depinject.ap.engine.Utils;

public class NoCandidates extends RuntimeException {
  public final BeanReference beanReference;
  public final BeanConfigTree configTree;

  public NoCandidates(BeanReference beanReference, BeanConfigTree configTree) {
    super(Utils.asStr(beanReference.sourceClass) + " in " + beanReference.place.display()
        + configTree.asStr(false, true));
    this.beanReference = beanReference;
    this.configTree = configTree;
  }
}
