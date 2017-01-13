package kz.greetgo.depinject.gen2;

import java.util.Collections;
import java.util.List;

import static kz.greetgo.depinject.gen2.Utils.asStr;
import static kz.greetgo.depinject.gen2.Utils.codeName;

public class BeanCreationWithDefaultConstructor extends BeanCreation {
  public BeanCreationWithDefaultConstructor(Class<?> beanClass, boolean singleton) {
    super(beanClass, singleton);
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.emptyList();
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

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(codeName(beanClass) + ' ' + variableName + " = new " + codeName(beanClass) + "();");
  }
}
