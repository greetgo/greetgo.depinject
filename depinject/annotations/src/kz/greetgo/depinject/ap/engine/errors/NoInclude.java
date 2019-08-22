package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.ap.message.Message;
import kz.greetgo.depinject.ap.message.MessageLevel;

import javax.lang.model.element.TypeElement;

public class NoInclude extends Message {

  public final TypeElement mustContainingClass;

  public NoInclude(TypeElement mustContainingClass) {
    super("In " + mustContainingClass.toString());
    this.mustContainingClass = mustContainingClass;
  }

  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
