package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.ScanPackage;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static kz.greetgo.depinject.gen.Utils.findDeclaredField;
import static org.fest.assertions.api.Assertions.assertThat;

public class UtilsTest {

  @Bean
  @SuppressWarnings("deprecation")
  @ScanPackage("Super")
  interface Super {}

  @SuppressWarnings("deprecation")
  @ScanPackage("Super2")
  interface Super2 {}

  @SuppressWarnings("deprecation")
  @ScanPackage("My")
  interface My extends Super, Super2 {}

  @Test
  public void getAnnotation_class1() {
    Bean bean = Utils.getAnnotation(My.class, Bean.class);
    assertThat(bean).isNotNull();
  }

  @SuppressWarnings("deprecation")
  @ScanPackage("My2")
  interface My2 extends Super2 {}

  @Test
  public void getAnnotation_class2() {
    Bean bean = Utils.getAnnotation(My2.class, Bean.class);
    assertThat(bean).isNull();
  }

  @SuppressWarnings("deprecation")
  @ScanPackage("My3")
  class My3 implements Super2, Super {}


  @SuppressWarnings("deprecation")
  @ScanPackage("Super3")
  interface Super3 {}

  @SuppressWarnings("deprecation")
  @ScanPackage("My4")
  class My4 extends My3 implements Super3 {}

  @Test
  public void getAnnotation_class3() {
    Bean bean = Utils.getAnnotation(My4.class, Bean.class);
    assertThat(bean).isNotNull();
  }

  @Test
  public void getAllAnnotations_class1() {
    List<Bean> list = Utils.getAllAnnotations(My.class, Bean.class);
    assertThat(list).hasSize(1);
  }

  @Test
  public void getAllAnnotations_class2() {
    List<Bean> list = Utils.getAllAnnotations(My2.class, Bean.class);
    assertThat(list).isEmpty();
  }

  @Test
  public void getAllAnnotations_class3() {
    List<Bean> list = Utils.getAllAnnotations(My4.class, Bean.class);
    assertThat(list).hasSize(1);
  }

  @Test
  public void getAllAnnotations_class4() {
    //noinspection deprecation
    List<ScanPackage> list = Utils.getAllAnnotations(My4.class, ScanPackage.class);
    //noinspection deprecation
    list.forEach(ann -> assertThat(ann).isInstanceOf(ScanPackage.class));
    assertThat(list).hasSize(5);
  }

  public interface WithoutSuperclass {
    @SuppressWarnings("unused")
    void testMethod();
  }

  @Test
  public void getAnnotation_noSuperclass() throws Exception {
    Method testMethod = WithoutSuperclass.class.getMethod("testMethod");
    System.out.println(testMethod);

    //
    //
    Bean annotation = Utils.getAnnotation(testMethod, Bean.class);
    //
    //

    assertThat(annotation).isNull();

  }

  public static class SomeFieldNotFound extends RuntimeException {}

  class ClassWithField1 {
    @SuppressWarnings("unused")
    private String someField432535;
  }

  @Test
  public void findDeclaredField_here() {
    Field field = findDeclaredField(ClassWithField1.class, "someField432535")
        .orElseThrow(SomeFieldNotFound::new);

    assertThat(field).isNotNull();
    assertThat(field.getName()).isEqualTo("someField432535");
    assertThat(field.getDeclaringClass().getName()).isEqualTo(ClassWithField1.class.getName());
  }

  class ClassWithField2_parent2 {
    @SuppressWarnings("unused")
    private String someField8924746;
  }

  class ClassWithField2 extends ClassWithField2_parent2 {}

  @Test
  public void findDeclaredField_fromParent() {
    Field field = findDeclaredField(ClassWithField2.class, "someField8924746")
        .orElseThrow(SomeFieldNotFound::new);

    assertThat(field).isNotNull();
    assertThat(field.getName()).isEqualTo("someField8924746");
    assertThat(field.getDeclaringClass().getName()).isEqualTo(ClassWithField2_parent2.class.getName());
  }

  class ClassWithField3_parentParent {
    @SuppressWarnings("unused")
    private String someField7173897;
  }

  class ClassWithField3_parent extends ClassWithField3_parentParent {}

  class ClassWithField3 extends ClassWithField3_parent {}

  @Test
  public void findDeclaredField_fromParentParent() {
    Field field = findDeclaredField(ClassWithField3.class, "someField7173897")
        .orElseThrow(SomeFieldNotFound::new);

    assertThat(field).isNotNull();
    assertThat(field.getName()).isEqualTo("someField7173897");
    assertThat(field.getDeclaringClass().getName()).isEqualTo(ClassWithField3_parentParent.class.getName());
  }

  @Test
  public void findDeclaredField_notFound() {
    Optional<Field> field = findDeclaredField(ClassWithField3.class, "some left name");

    assertThat(field).isNotNull();
    assertThat(field.isPresent()).isFalse();
  }
}