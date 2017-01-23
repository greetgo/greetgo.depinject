package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.ScanPackage;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class UtilsTest {

  @Bean
  @ScanPackage("Super")
  interface Super {
  }

  @ScanPackage("Super2")
  interface Super2 {
  }

  @ScanPackage("My")
  interface My extends Super, Super2 {
  }

  @Test
  public void getAnnotation_class1() throws Exception {
    Bean bean = Utils.getAnnotation(My.class, Bean.class);
    assertThat(bean).isNotNull();
  }

  @ScanPackage("My2")
  interface My2 extends Super2 {
  }

  @Test
  public void getAnnotation_class2() throws Exception {
    Bean bean = Utils.getAnnotation(My2.class, Bean.class);
    assertThat(bean).isNull();
  }

  @ScanPackage("My3")
  class My3 implements Super2, Super {
  }

  @ScanPackage("Super3")
  interface Super3 {
  }

  @ScanPackage("My4")
  class My4 extends My3 implements Super3 {
  }

  @Test
  public void getAnnotation_class3() throws Exception {
    Bean bean = Utils.getAnnotation(My4.class, Bean.class);
    assertThat(bean).isNotNull();
  }

  @Test
  public void getAllAnnotations_class1() throws Exception {
    List<Bean> list = Utils.getAllAnnotations(My.class, Bean.class);
    assertThat(list).hasSize(1);
  }

  @Test
  public void getAllAnnotations_class2() throws Exception {
    List<Bean> list = Utils.getAllAnnotations(My2.class, Bean.class);
    assertThat(list).isEmpty();
  }

  @Test
  public void getAllAnnotations_class3() throws Exception {
    List<Bean> list = Utils.getAllAnnotations(My4.class, Bean.class);
    assertThat(list).hasSize(1);
  }

  @Test
  public void getAllAnnotations_class4() throws Exception {
    List<ScanPackage> list = Utils.getAllAnnotations(My4.class, ScanPackage.class);
    list.forEach(ann -> assertThat(ann).isInstanceOf(ScanPackage.class));
    assertThat(list).hasSize(5);
  }

  public static interface WithoutSuperclass {
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
}