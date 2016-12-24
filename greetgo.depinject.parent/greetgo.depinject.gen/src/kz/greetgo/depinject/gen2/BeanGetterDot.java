package kz.greetgo.depinject.gen2;

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
}
