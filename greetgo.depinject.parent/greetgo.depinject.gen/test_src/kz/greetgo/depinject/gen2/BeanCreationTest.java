package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanCreationTest {

  class Bean1 {
  }

  class Bean2 {
  }

  public class ParentBean {
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

    //
    //
    BeanCreation.fillBeanGetterDotListInner(list, MainBean.class);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).fieldName).isEqualTo("field1");
    assertThat(list.get(1).fieldName).isEqualTo("field2");
    assertThat(list.get(2).fieldName).isEqualTo("parentField");

    assertThat(list.get(0).beanReference.targetClass().getName()).isEqualTo(Bean1.class.getName());
    assertThat(list.get(1).beanReference.targetClass().getName()).isEqualTo(Bean2.class.getName());

    assertThat(list.get(0).beanReference.isList()).isFalse();
    assertThat(list.get(1).beanReference.isList()).isTrue();

  }

  public class LeftBean {

    @SuppressWarnings("unused")
    public BeanGetter field1;

  }

  @Test(expectedExceptions = IllegalBeanGetterDefinition.class)
  public void fillBeanGetterDotListInner_IllegalBeanGetterDefinition() throws Exception {
    //
    //
    BeanCreation.fillBeanGetterDotListInner(new ArrayList<>(), LeftBean.class);
    //
    //
  }
}