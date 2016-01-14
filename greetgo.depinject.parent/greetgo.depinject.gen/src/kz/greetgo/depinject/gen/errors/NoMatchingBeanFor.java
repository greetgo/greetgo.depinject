package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Type;

public class NoMatchingBeanFor extends RuntimeException {
  
  public final Type fieldType;
  
  public NoMatchingBeanFor(Type fieldType) {
    super(fieldType.toString());
    this.fieldType = fieldType;
  }
  
}
