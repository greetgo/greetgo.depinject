package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.SkipInject;
import kz.greetgo.depinject.gen.errors.NonPublicBeanWithoutConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanCreationWithConstructor extends BeanCreation {
  final List<ConstructorArg> argList;

  public BeanCreationWithConstructor(Context context,
                                     Class<?> beanClass,
                                     Bean bean,
                                     List<ConstructorArg> argList) {
    super(context, beanClass, bean);
    Objects.requireNonNull(argList, "argList == null");
    this.argList = argList;
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return argList.stream()
        .map(ConstructorArg::beanReference)
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return (use ? '{' : '(')
        + Utils.asStr(beanClass) + (bean.singleton() ? ":SINGLE" : "MANY") + " created by constructor("
        + argList.stream()
        .map(ConstructorArg::displayStr)
        .collect(Collectors.joining(", "))
        + ")"
        + preparationInfo()
        + (use ? '}' : ')');
  }

  @Override
  protected void markToUseAdditions() {
    argList.forEach(ConstructorArg::markToUse);
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName + " = new " + Utils.codeName(beanClass)
        + "(" + (
        argList.stream()
            .map(ConstructorArg::referenceExpression)
            .collect(Collectors.joining(", "))
    ) + ");");
  }

  @Override
  public void checkBeanGetterNotPublic() {
    checkBeanGetterNotPublicFor(beanClass, null);

    Class<?> me = beanClass;

    while (true) {
      Class<?> parent = me.getSuperclass();
      if (Object.class.equals(parent)) {
        return;
      }
      checkBeanGetterNotPublicFor(parent, beanClass);
      me = parent;
    }
  }

  private void checkBeanGetterNotPublicFor(Class<?> aClass, Class<?> beanClass) {
    if (aClass.getAnnotation(SkipInject.class) != null) {
      return;
    }

    Set<String> argTypes = argList.stream()
        .map(a -> a.argType.toString())
        .collect(Collectors.toSet());

    for (Field field : aClass.getDeclaredFields()) {
      if (Modifier.isPublic(field.getModifiers())) {
        continue;
      }

      if (!BeanGetter.class.equals(field.getType())) {
        continue;
      }

      if (field.getAnnotation(SkipInject.class) != null) {
        continue;
      }

      if (argTypes.contains(field.getGenericType().toString())) {
        continue;
      }

      throw new NonPublicBeanWithoutConstructor(aClass, field, beanClass);
    }
  }
}
