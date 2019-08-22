package kz.greetgo.depinject.ann.util;

public class ValueEntry {
  public final String value;
  public final String typeQualifiedName;

  public ValueEntry(Object value) {
    this.value = "" + value;
    this.typeQualifiedName = value.getClass().getName();
  }

  @Override
  public String toString() {
    return value;
  }
}
