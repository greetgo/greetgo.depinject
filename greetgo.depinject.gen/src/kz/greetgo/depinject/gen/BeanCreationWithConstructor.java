package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.SkipInject;
import kz.greetgo.depinject.gen.errors.BeanGetterIsNotPublic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanCreationWithConstructor extends BeanCreation {
  final List<ConstructorArg> argList;

  public BeanCreationWithConstructor(Context context,
                                     Class<?> beanClass,
                                     boolean singleton,
                                     List<ConstructorArg> argList) {
    super(context, beanClass, singleton);
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
        + Utils.asStr(beanClass) + (singleton ? ":SINGLE" : "MANY") + " created by constructor("
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
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName + " = new " + Utils.codeName(beanClass) + "();");
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

  private static void checkBeanGetterNotPublicFor(Class<?> aClass, Class<?> beanClass) {
    if (aClass.getAnnotation(SkipInject.class) != null) {
      return;
    }

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

      throw new BeanGetterIsNotPublic(aClass, field, beanClass);
    }
  }
}
