package kz.greetgo.depinject.gen;

import java.util.Objects;

public class BeanGetterInPublicField implements Comparable<BeanGetterInPublicField> {
  public final String fieldName;
  public final BeanReference beanReference;

  public BeanGetterInPublicField(String fieldName, BeanReference beanReference) {
    Objects.requireNonNull(fieldName, "fieldName == null");
    Objects.requireNonNull(beanReference, "beanReference == null");

    this.fieldName = fieldName;
    this.beanReference = beanReference;
  }

  @Override
  public int compareTo(BeanGetterInPublicField o) {
    return fieldName.compareTo(o.fieldName);
  }

  @Override
  public String toString() {
    return fieldName + " : " + beanReference.toFullString();
  }

  public void writeAssignment(int tab, Outer out, String variableName) {
    out.tab(tab).stn(variableName + '.' + fieldName + " = " + beanReference.accessExpression() + ";");
  }
}
