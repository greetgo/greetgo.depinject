package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.BeanConfigTree;
import kz.greetgo.depinject.gen.BeanReference;
import kz.greetgo.depinject.gen.Utils;

public class ManyCandidates extends RuntimeException {
  public final BeanReference beanReference;
  public final BeanConfigTree configTree;

  public ManyCandidates(BeanReference beanReference, BeanConfigTree configTree) {
    super(createMessage(beanReference, configTree));
    this.beanReference = beanReference;
    this.configTree = configTree;
  }

  private static String createMessage(BeanReference beanReference, BeanConfigTree configTree) {
    StringBuilder sb = new StringBuilder();
    sb.append(Utils.asStr(beanReference.sourceClass))
      .append(" -> ")
      .append(beanReference.getterCreations.size())
      .append(" refs @ ").append(beanReference.place).append("\nCandidates:\n");
    beanReference.getterCreations.forEach(bc -> sb.append("\n\t").append(bc));
    sb.append("\n").append(configTree.asStr(false, true));
    return sb.toString();
  }
}
