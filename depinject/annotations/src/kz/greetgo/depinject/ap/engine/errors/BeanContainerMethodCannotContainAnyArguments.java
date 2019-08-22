package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.ap.message.Message;
import kz.greetgo.depinject.ap.message.MessageLevel;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class BeanContainerMethodCannotContainAnyArguments extends Message {
  public final TypeElement beanContainerInterface;
  public final ExecutableElement method;

  public BeanContainerMethodCannotContainAnyArguments(TypeElement beanContainerInterface, ExecutableElement method) {
    super(beanContainerInterface.getSimpleName() + "." + method.getSimpleName());
    this.beanContainerInterface = beanContainerInterface;
    this.method = method;
  }

  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
