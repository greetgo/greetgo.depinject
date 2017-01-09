package kz.greetgo.depinject.gen2;

import java.io.PrintStream;
import java.lang.reflect.Method;

import static kz.greetgo.depinject.gen2.Tab.tab;
import static kz.greetgo.depinject.gen2.Utils.asStr;

public class BeanCreationWithFactoryMethod extends BeanCreation {
  public final BeanCreation factorySource;
  public final Method factoryMethod;

  public BeanCreationWithFactoryMethod(Class<?> beanClass, boolean singleton,
                                       BeanCreation factorySource, Method factoryMethod) {
    super(beanClass, singleton);
    this.factorySource = factorySource;
    this.factoryMethod = factoryMethod;
  }


  @Override
  public String toString() {
    return (use ? '{' : '(')
      + asStr(beanClass) + (singleton ? ":SINGLE" : "MULT")
      + " created by method " + factoryMethod.getName() + "() of " + factorySource
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  protected void markToUseAdditions() {
    factorySource.markToUse();
  }

  @Override
  protected void writeCreateBeanCode(int tab, PrintStream out, String variableName) {
    out.println(tab(tab) + beanClass.getName() + ' ' + variableName
      + " = " + factorySource.getterVarName() + ".get()." + factoryMethod.getName() + "();");
  }
}
