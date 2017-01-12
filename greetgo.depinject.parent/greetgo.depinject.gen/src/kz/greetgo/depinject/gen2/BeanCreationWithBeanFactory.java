package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanFactory;

import java.util.Collections;
import java.util.List;

import static kz.greetgo.depinject.gen2.Utils.asStr;
import static kz.greetgo.depinject.gen2.Utils.codeName;

public class BeanCreationWithBeanFactory extends BeanCreation {
  public final BeanReference beanFactorySource;

  public BeanCreationWithBeanFactory(Class<?> beanClass, boolean singleton, BeanReference beanFactorySource) {
    super(beanClass, singleton);
    if (!BeanFactory.class.isAssignableFrom(beanFactorySource.targetClass)) {
      throw new RuntimeException(beanFactorySource.targetClass + " is not bean factory; " + beanFactorySource.place);
    }
    this.beanFactorySource = beanFactorySource;
  }

  @Override
  public String toString() {
    return (use ? '{' : '(')
      + asStr(beanClass) + (singleton ? ":SINGLE" : "MULT")
      + " created by " + beanFactorySource.firstBeanToString()
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.singletonList(beanFactorySource);
  }

  @Override
  protected void markToUseAdditions() {
    beanFactorySource.markToUse();
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(codeName(beanClass) + ' ' + variableName
      + " = (" + codeName(beanClass) + ") " + beanFactorySource.getterVarName()
      + ".get().createBean(" + codeName(beanClass) + ".class);");
  }
}
