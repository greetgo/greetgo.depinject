package kz.greetgo.depinject.gen;

import java.util.Collections;
import java.util.List;

public class BeanCreationWithDefaultConstructor extends BeanCreation {
  public BeanCreationWithDefaultConstructor(Context context, Class<?> beanClass, boolean singleton) {
    super(context, beanClass, singleton);
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.emptyList();
  }

  @Override
  public String toString() {
    return (use ? '{' : '(')
      + Utils.asStr(beanClass) + (singleton ? ":SINGLE" : "MULT") + " created by def constructor"
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  protected void markToUseAdditions() {
    //Nothing to do
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName + " = new " + Utils.codeName(beanClass) + "();");
  }
}
