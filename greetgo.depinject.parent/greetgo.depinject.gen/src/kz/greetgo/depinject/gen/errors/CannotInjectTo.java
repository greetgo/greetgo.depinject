package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Field;

public class CannotInjectTo extends RuntimeException {
  public final Field field;

  public CannotInjectTo(Field field) {
    super(field.toGenericString());
    this.field = field;
  }
}
