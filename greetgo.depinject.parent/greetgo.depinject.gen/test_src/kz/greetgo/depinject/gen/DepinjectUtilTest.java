package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.impl_and_use_bean_containers.BeanContainerForTestingUtil;
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
  public void typeToClass_class() throws Exception {
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
      "kz.greetgo.depinject.gen.impl_and_use_bean_containers",
      srcDir
    );
    //
    //
    //

    final BeanContainerForTestingUtil impl = Depinject.newInstance(BeanContainerForTestingUtil.class);

    impl.getIBeanB1().privet();

    ServerUtil.deleteRecursively(srcDir);
  }
}
