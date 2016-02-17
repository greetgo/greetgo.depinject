package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static kz.greetgo.depinject.gen.DepinjectUtil.spaces;
import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;

public class InjectorList extends Injector {
  public final BeanDefinition owner;
  public final Field field;
  public final List<BeanDefinition> sourceList;

  public InjectorList(BeanDefinition owner, Field field, List<BeanDefinition> sourceList) {
    this.owner = owner;
    this.field = field;
    this.sourceList = unmodifiableList(sourceList);
  }

  @Override
  public String compareStr() {
    return field.getName();
  }

  @Override
  public List<BeanDefinition> sourceList() {
    return sourceList;
  }

  @Override
  public void intoVariable(PrintWriter writer, String variableName, int spaces) {
    String s1 = spaces(spaces);
    String s2 = spaces(spaces + 2);
    String s3 = spaces(spaces + 4);
    String list = List.class.getName();
    String arrayList = ArrayList.class.getName();
    writer.println(s1 + variableName + "." + field.getName() + " = (" + toCode(field.getGenericType())
      + ")(Object)new " + BeanGetter.class.getName() + "() {");
    writer.println(s2 + "public Object get() {");
    writer.println(s3 + list + " ret = new " + arrayList + "();");
    for (BeanDefinition source : sourceList) {
      writer.println(s3 + "ret.add(" + source.getterName + ".get());");
    }
    writer.println(s3 + "return ret;");
    writer.println(s2 + "}");
    writer.println(s1 + "};");

  }
}
