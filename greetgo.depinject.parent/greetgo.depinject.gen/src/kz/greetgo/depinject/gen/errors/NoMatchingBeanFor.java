package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Type;

public class NoMatchingBeanFor extends RuntimeException {

  public final Type fieldType;
  public final String place;

  public NoMatchingBeanFor(Type fieldType, String place) {
    super(fieldType.toString() + " in " + place);
    this.fieldType = fieldType;
    this.place = place;
  }

}
