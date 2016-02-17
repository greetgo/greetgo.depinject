package kz.greetgo.depinject.gen;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static kz.greetgo.depinject.gen.DepinjectUtil.spaces;
import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;

public class InjectorSingle extends Injector {
  public final BeanDefinition owner;
  public final Field field;
  public final BeanDefinition source;

  public InjectorSingle(BeanDefinition owner, Field field, BeanDefinition source) {
    this.owner = owner;
    this.field = field;
    this.source = source;
  }

  @Override
  public String compareStr() {
    return field.getName();
  }

  @Override
  public List<BeanDefinition> sourceList() {
    List<BeanDefinition> ret = new ArrayList<>();
    ret.add(source);
    return unmodifiableList(ret);
  }

  @Override
  public void intoVariable(PrintWriter writer, String variableName, int spaces) {
    String s = spaces(spaces);
    writer.println(s + variableName + "." + field.getName() + " = (" + toCode(field.getGenericType())
      + ")(Object)" + source.getterName + ";");
  }
}
