package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.test_beans027.beans.afterInject_sync.DependsOnAlone;
import kz.greetgo.depinject.gen.test_beans027.container.BeanContainerForTestingUtil;
import kz.greetgo.depinject.gen.test_beans028.NoIncludeBeanContainer;
import kz.greetgo.util.ServerUtil;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class DepinjectUtilTest {

  private class A {
    @SuppressWarnings("unused")
    public List<String> x;
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
    final String srcDir = "build/implementAndUseBeanContainers";

    //
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
      "kz.greetgo.depinject.gen.test_beans027.container",
      srcDir
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

    ServerUtil.deleteRecursively(srcDir);
  }

  @Test(enabled = false)
  public void simpleCall() {
    final BeanContainerForTestingUtil impl = Depinject.newInstance(BeanContainerForTestingUtil.class);

    DependsOnAlone dependsOnAlone = impl.getDependsOnAlone();
    System.out.println(dependsOnAlone.valueInitiatedWhileAfterInject);
  }

  @Test
  public void implementAndUseBeanContainers_NoIncludeBeanContainer() throws Exception {
    final String srcDir = "build/implementAndUseBeanContainers_NoIncludeBeanContainer";

    //
    //
    //
    DepinjectUtil.implementAndUseBeanContainers(
      NoIncludeBeanContainer.class.getPackage().getName(),
      srcDir
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
}
