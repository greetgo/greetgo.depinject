package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ContextTest2 {

  class Bean1 {
  }

  class Bean2 {
  }

  public class ParentBean {
    @SuppressWarnings("unused")
    public BeanGetter<Bean1> parentField;
  }

  @SuppressWarnings("unused")
  public class MainBean extends ParentBean {

    public BeanGetter<List<Bean2>> field2;

    public BeanGetter<Bean1> field1;

  }

  @Test
  public void fillBeanGetterDotListInner() throws Exception {
    List<BeanGetterDot> list = new ArrayList<>();

    Context context = new Context();

    //
    //
    context.fillBeanGetterDotListInner(list, MainBean.class);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).fieldName).isEqualTo("field1");
    assertThat(list.get(1).fieldName).isEqualTo("field2");
    assertThat(list.get(2).fieldName).isEqualTo("parentField");

    assertThat(list.get(0).beanReference.sourceClass.getName()).isEqualTo(Bean1.class.getName());
    assertThat(list.get(1).beanReference.sourceClass.getName()).isEqualTo(Bean2.class.getName());

    assertThat(list.get(0).beanReference.isList).isFalse();
    assertThat(list.get(1).beanReference.isList).isTrue();

  }

  public class LeftBean {

    @SuppressWarnings("unused")
    public BeanGetter field1;

  }

  @Test(expectedExceptions = IllegalBeanGetterDefinition.class)
  public void fillBeanGetterDotListInner_IllegalBeanGetterDefinition() throws Exception {
    Context context = new Context();

    //
    //
    context.fillBeanGetterDotListInner(new ArrayList<>(), LeftBean.class);
    //
    //
  }

  interface I1 extends BeanPreparation<Bean1> {
  }

  @SuppressWarnings("unused")
  interface I2<T> {
  }

  class C1 implements I1, I2<String> {
    @Override
    public Bean1 prepareBean(Bean1 bean) {
      return null;
    }
  }

  @Test
  public void getPreparingClassInner() throws Exception {
    //
    //
    Class<?> result = BeanCreation.getPreparingClass(C1.class, new HashSet<>());
    //
    //

    if (result == null) { throw new NullPointerException(); }

    assertThat(result.getName()).isEqualTo(Bean1.class.getName());
  }

  class C2 implements I2<String> {
  }

  @Test
  public void getPreparingClassInner_null() throws Exception {
    //
    //
    Class<?> result = BeanCreation.getPreparingClass(C2.class, new HashSet<>());
    //
    //

    assertThat(result).isNull();
  }
}