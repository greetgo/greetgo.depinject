package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;

public class BeanGetterDot implements Comparable<BeanGetterDot> {
  public final String fieldName;
  public final BeanReference beanReference;

  public BeanGetterDot(String fieldName, BeanReference beanReference) {
    if (fieldName == null) throw new NullPointerException("fieldName == null");
    if (beanReference == null) throw new NullPointerException("beanReference == null");
    this.fieldName = fieldName;
    this.beanReference = beanReference;
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public int compareTo(BeanGetterDot o) {
    return fieldName.compareTo(o.fieldName);
  }

  @Override
  public String toString() {
    return fieldName + " : " + beanReference.toFullString();
  }

  public void writeAssignment(int tab, Outer out, String variableName) {
    out.tab(tab).stn(variableName + '.' + fieldName + " = (" + Utils.codeName(BeanGetter.class) + '<'
      + beanReference.targetClassCode() + ">)(java.lang.Object)" + beanReference.getterVarName() + ";");

  }
}
