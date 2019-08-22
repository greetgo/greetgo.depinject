package kz.greetgo.depinject.ann.util;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import java.util.Optional;

public abstract class GetOptional<R, P> implements TypeVisitor<Optional<R>, P> {
  @Override
  public Optional<R> visit(TypeMirror t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visit(TypeMirror t) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitPrimitive(PrimitiveType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitNull(NullType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitArray(ArrayType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitDeclared(DeclaredType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitError(ErrorType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitTypeVariable(TypeVariable t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitWildcard(WildcardType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitExecutable(ExecutableType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitNoType(NoType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitUnknown(TypeMirror t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitUnion(UnionType t, P p) {
    return Optional.empty();
  }

  @Override
  public Optional<R> visitIntersection(IntersectionType t, P p) {
    return Optional.empty();
  }
}
