package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.test_beans027.beans.afterInject_sync.DependsOnAlone;
import kz.greetgo.depinject.gen.test_beans027.container.BeanContainerForTestingUtil;
import kz.greetgo.depinject.gen.test_beans028.NoIncludeBeanContainer;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.bean_container.BeanContainer033_01;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.Bean03301_1;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.Bean03301_2;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.Bean03301_3;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.MainBean03301;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.bean_container.BeanContainer03302;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.Bean03302_11;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.Bean03302_12;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.Bean03302_21;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.Bean03302_22;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.MainBean03302;
import kz.greetgo.depinject.gen.test_beans033.p02_list_bean_getters_in_constructor.beans.MainBeanImpl03302;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.bean_container.BeanContainer03303;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group1.Bean03303_11;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group1.Bean03303_12;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group2.Bean03303_21;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.group2.Bean03303_22;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.main.MainBean03303;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.main.MainBeanImpl03303;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.simple.SimpleBean03303_1;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.simple.SimpleBean03303_2;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepinjectUtilTest {

  private class A {
    @SuppressWarnings("unused")
    public List<String> x;
  }

  private String srcDir(String testName) {
    return Paths.get("build", "test_generated_src", getClass().getSimpleName(), testName).toString();
  }

  @Test
  public void typeToClass_genericType() throws Exception {
    final Field x = A.class.getField("x");

    //
    //
    final Class<?> aClass = DepinjectUtil.typeToClass(x.getGenericType());
    //
    //

    assertThat(aClass.getName()).isEqualTo(List.class.getName());

  }

  @Test
  public void typeToClass_class() {
    //
    //
    final Class<?> aClass = DepinjectUtil.typeToClass(String.class);
    //
    //

    assertThat(aClass.getName()).isEqualTo(String.class.getName());

  }

  @SuppressWarnings("unused")
  private interface ForTestingOfToCode {
    List<String> generic();

    String simple();
  }

  @Test
  public void toCode_generic() throws Exception {
    final Type generic = ForTestingOfToCode.class.getMethod("generic").getGenericReturnType();
    //
    //
    final String s = DepinjectUtil.toCode(generic);
    //
    //
    assertThat(s).isEqualTo("java.util.List<java.lang.String>");
  }

  @Test
  public void toCode_simple() throws Exception {
    final Type generic = ForTestingOfToCode.class.getMethod("simple").getGenericReturnType();
    //
    //
    final String s = DepinjectUtil.toCode(generic);
    //
    //
    assertThat(s).isEqualTo("java.lang.String");
  }

  @Test
  public void implementAndUseBeanContainers() throws Exception {
    //
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
        "kz.greetgo.depinject.gen.test_beans027.container",
        srcDir("implementAndUseBeanContainers")
    );
    //
    //
    //

    final BeanContainerForTestingUtil impl = Depinject.newInstance(BeanContainerForTestingUtil.class);

    String privet = impl.getIBeanB1().privet();
    assertThat(privet).isEqualTo("Privet");

    String callInterfaceCreatingWithA = impl.getIBeanB1().callInterfaceCreatingWithA();
    assertThat(callInterfaceCreatingWithA).isEqualTo("Hello from interface creating with A");

    String callAnotherInterfaceCreatingWithA = impl.getIBeanB1().callAnotherInterfaceCreatingWithA();
    assertThat(callAnotherInterfaceCreatingWithA).isEqualTo("Hello from another interface creating with A");

    String callCreatingByDefaultFactory = impl.getIBeanB1().callCreatingByDefaultFactory();
    assertThat(callCreatingByDefaultFactory).isEqualTo("Hello from CreatingByDefaultFactory");

    DependsOnAlone dependsOnAlone = impl.getDependsOnAlone();
    assertThat(dependsOnAlone.valueInitiatedWhileAfterInject)
        .isEqualTo("DependsOnAlone value : Value while after inject");
  }

  @Test(enabled = false)
  public void simpleCall() {
    final BeanContainerForTestingUtil impl = Depinject.newInstance(BeanContainerForTestingUtil.class);

    DependsOnAlone dependsOnAlone = impl.getDependsOnAlone();
    System.out.println(dependsOnAlone.valueInitiatedWhileAfterInject);
  }

  @Test
  public void implementAndUseBeanContainers_NoIncludeBeanContainer() throws Exception {
    //
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
        NoIncludeBeanContainer.class.getPackage().getName(),
        srcDir("implementAndUseBeanContainers_NoIncludeBeanContainer")
    );
    //
    //
    //
  }

  @Test
  public void version() {
    //
    //
    DepinjectVersion version = DepinjectUtil.version();
    //
    //

    assertThat(version).isNotNull();
    assertThat(version.version1).isGreaterThan(0);
  }

  @Test
  public void p01_bean_getters_in_constructor() throws Exception {
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
        BeanContainer033_01.class.getPackage().getName(),
        srcDir("p01_bean_getters_in_constructor")
    );
    //
    //

    BeanContainer033_01 beanContainer = Depinject.newInstance(BeanContainer033_01.class);

    MainBean03301 mainBean033 = beanContainer.mainBean();

    StringBuilder out = new StringBuilder();

    mainBean033.hello(out);

//    System.out.println(out);

    assertThat(out.toString()).contains("Hello from " + MainBean03301.class.getSimpleName());
    assertThat(out.toString()).contains("Hello from " + Bean03301_1.class.getSimpleName());
    assertThat(out.toString()).contains("Hello from " + Bean03301_2.class.getSimpleName());
    assertThat(out.toString()).contains("Hello from " + Bean03301_3.class.getSimpleName());
  }

  @Test
  public void p02_list_bean_getters_in_constructor() throws Exception {
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
        BeanContainer03302.class.getPackage().getName(),
        srcDir("p02_list_bean_getters_in_constructor")
    );
    //
    //

    BeanContainer03302 beanContainer = Depinject.newInstance(BeanContainer03302.class);

    MainBean03302 mainBean = beanContainer.mainBean();

    StringBuilder out = new StringBuilder();

    mainBean.hello(out);

//    System.out.println(out);

    assertThat(out.toString()).contains("Hello from " + MainBeanImpl03302.class.getSimpleName());
    assertThat(out.toString()).contains("Hello1 from " + Bean03302_11.class.getSimpleName());
    assertThat(out.toString()).contains("Hello2 from " + Bean03302_12.class.getSimpleName());
    assertThat(out.toString()).contains("Hi1 from " + Bean03302_21.class.getSimpleName());
    assertThat(out.toString()).contains("Hi2 from " + Bean03302_22.class.getSimpleName());
  }

  @Test
  public void p03_bean_getters_all_combinations() throws Exception {
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
        BeanContainer03303.class.getPackage().getName(),
        srcDir("p03_bean_getters_all_combinations")
    );
    //
    //

    BeanContainer03303 beanContainer = Depinject.newInstance(BeanContainer03303.class);

    MainBean03303 mainBean = beanContainer.mainBean();

    StringBuilder out = new StringBuilder();

    mainBean.hello(out);

//    System.out.println(out);

    assertThat(out.toString()).contains("Hello from " + MainBeanImpl03303.class.getSimpleName());
    assertThat(out.toString()).contains("Hello1 from " + Bean03303_11.class.getSimpleName());
    assertThat(out.toString()).contains("Hello1 from " + Bean03303_12.class.getSimpleName());
    assertThat(out.toString()).contains("Hello2 from " + Bean03303_21.class.getSimpleName());
    assertThat(out.toString()).contains("Hello2 from " + Bean03303_22.class.getSimpleName());
    assertThat(out.toString()).contains("Hi from " + SimpleBean03303_1.class.getSimpleName());
    assertThat(out.toString()).contains("Hi from " + SimpleBean03303_2.class.getSimpleName());
  }
}
