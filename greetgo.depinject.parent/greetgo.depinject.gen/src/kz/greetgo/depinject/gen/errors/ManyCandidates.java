package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.BeanReference;
import kz.greetgo.depinject.gen.Utils;

public class ManyCandidates extends RuntimeException {
  public final BeanReference beanReference;

  public ManyCandidates(BeanReference beanReference) {
    super(createMessage(beanReference));
    this.beanReference = beanReference;
  }

  private static String createMessage(BeanReference beanReference) {
    StringBuilder sb = new StringBuilder();
    sb.append(Utils.asStr(beanReference.sourceClass))
      .append(" -> ")
      .append(beanReference.getterCreations.size())
      .append(" refs @ ").append(beanReference.place).append("\nCandidates:\n");
    beanReference.getterCreations.forEach(bc -> sb.append("\n\t").append(bc));
    return sb.toString();
  }
}
