package kz.greetgo.depinject.ann.util;

public class QualifierData {
  public final String value;
  public final boolean regexp;

  public QualifierData(String value, boolean regexp) {
    this.value = value;
    this.regexp = regexp;
  }

  public static QualifierData of(String value, boolean regexp) {
    return new QualifierData(value, regexp);
  }

  public static QualifierData absent() {
    return of("", false);
  }
}
