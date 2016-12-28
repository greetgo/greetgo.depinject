package kz.greetgo.depinject.gen2;

import static kz.greetgo.depinject.gen2.Utils.asStr;

public class BeanCreationWithDefaultConstructor extends BeanCreation {
  public BeanCreationWithDefaultConstructor(Class<?> beanClass, boolean singleton) {
    super(beanClass, singleton);
  }

  @Override
  public String toString() {
    return (use ? '{' : '(')
      + asStr(beanClass) + (singleton ? ":SINGLE" : "MULT") + " created by def constructor"
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  protected void markToUseAdditions() {
    //Nothing to do
  }
}
