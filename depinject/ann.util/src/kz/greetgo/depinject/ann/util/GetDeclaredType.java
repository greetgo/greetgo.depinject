package kz.greetgo.depinject.ann.util;

import javax.lang.model.type.DeclaredType;
import java.util.Optional;

public class GetDeclaredType extends GetOptional<DeclaredType, Void> {

  private GetDeclaredType() {}

  private static final GetDeclaredType INSTANCE = new GetDeclaredType();

  public static GetDeclaredType get() {
    return INSTANCE;
  }

  @Override
  public Optional<DeclaredType> visitDeclared(DeclaredType t, Void aVoid) {
    return Optional.of(t);
  }
}
