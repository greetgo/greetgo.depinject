package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.ap.engine.BeanConfigTree;
import kz.greetgo.depinject.ap.engine.BeanCreation;
import kz.greetgo.depinject.ap.engine.BeanReference;
import kz.greetgo.depinject.ap.engine.Utils;

public class QualifierNotMatched extends RuntimeException {
  public final BeanReference beanReference;
  public final BeanConfigTree configTree;

  public QualifierNotMatched(BeanReference beanReference, BeanConfigTree configTree) {
    super(message(beanReference, configTree));
    this.beanReference = beanReference;
    this.configTree = configTree;
  }

  private static String message(BeanReference beanReference, BeanConfigTree configTree) {
    StringBuilder sb = new StringBuilder();
    sb.append(beanReference.place.display());

    sb.append("\n\tqualifier = \"").append(beanReference.place.qualifier()).append('"');

    for (BeanCreation beanCreation : beanReference.assignableCandidates) {
      sb.append("\n\t\tid = \"").append(beanCreation.beanId()).append("\"")
          .append(" in ").append(Utils.asStr(beanCreation.beanClass));
    }
    sb.append("\n");

    sb.append(configTree.asStr(false, true));

    return sb.toString();
  }
}
