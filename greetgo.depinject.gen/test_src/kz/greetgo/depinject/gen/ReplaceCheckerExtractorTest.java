package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.replace.ReplaceInstanceOf;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;
import kz.greetgo.depinject.gen.ReplaceChecker;
import org.testng.annotations.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static kz.greetgo.depinject.gen.ReplaceCheckerExtractor.fromBeanClass;
import static org.fest.assertions.api.Assertions.assertThat;

public class ReplaceCheckerExtractorTest {

  interface Iface {
  }

  interface Iface2 {
  }

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ann {
  }

  class BeanEmpty {
  }

  class BeanImplIface implements Iface {
  }

  @Ann
  class SomeBeanWithAnn {
  }

  @Ann
  class BeanImplIfaceWithAnn implements Iface {
  }


  @ReplaceInstanceOf(Iface.class)
  class Replacer_Iface {
  }

  @Test
  public void fromBeanClass_iface_empty() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface.class);
    assertThat(checker.check(BeanEmpty.class)).isFalse();
  }

  @Test
  public void fromBeanClass_iface_iface() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface.class);
    assertThat(checker.check(BeanImplIface.class)).isTrue();
  }

  @Test
  public void fromBeanClass_iface_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface.class);
    assertThat(checker.check(SomeBeanWithAnn.class)).isFalse();
  }

  @Test
  public void fromBeanClass_iface_iface_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface.class);
    assertThat(checker.check(BeanImplIfaceWithAnn.class)).isTrue();
  }

  @ReplaceInstanceOf({Iface.class, Iface2.class})
  class Replacer_Iface2 {
  }

  @Test
  public void fromBeanClass_iface2_empty() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface2.class);
    assertThat(checker.check(BeanEmpty.class)).isFalse();
  }

  @Test
  public void fromBeanClass_iface2_iface() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface2.class);
    assertThat(checker.check(BeanImplIface.class)).isTrue();
  }

  @Test
  public void fromBeanClass_iface2_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface2.class);
    assertThat(checker.check(SomeBeanWithAnn.class)).isFalse();
  }

  @Test
  public void fromBeanClass_iface2_iface_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface2.class);
    assertThat(checker.check(BeanImplIfaceWithAnn.class)).isTrue();
  }

  @ReplaceWithAnn(Ann.class)
  class Replacer_Ann {
  }

  @Test
  public void fromBeanClass_ann_empty() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Ann.class);
    assertThat(checker.check(BeanEmpty.class)).isFalse();
  }

  @Test
  public void fromBeanClass_ann_iface() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Ann.class);
    assertThat(checker.check(BeanImplIface.class)).isFalse();
  }

  @Test
  public void fromBeanClass_ann_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Ann.class);
    assertThat(checker.check(SomeBeanWithAnn.class)).isTrue();
  }

  @Test
  public void fromBeanClass_ann_iface_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Ann.class);
    assertThat(checker.check(BeanImplIfaceWithAnn.class)).isTrue();
  }

  @ReplaceInstanceOf(Iface.class)
  @ReplaceWithAnn(Ann.class)
  class Replacer_Iface_Ann {
  }

  @Test
  public void fromBeanClass_ifaceAnn_empty() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface_Ann.class);
    assertThat(checker.check(BeanEmpty.class)).isFalse();
  }

  @Test
  public void fromBeanClass_ifaceAnn_iface() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface_Ann.class);
    assertThat(checker.check(BeanImplIface.class)).isFalse();
  }

  @Test
  public void fromBeanClass_ifaceAnn_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface_Ann.class);
    assertThat(checker.check(SomeBeanWithAnn.class)).isFalse();
  }

  @Test
  public void fromBeanClass_ifaceAnn_iface_ann() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_Iface_Ann.class);
    assertThat(checker.check(BeanImplIfaceWithAnn.class)).isTrue();
  }

  @ReplaceInstanceOf({})
  class Replacer_IfaceEmpty {
  }

  @Test
  public void fromBeanClass_IfaceEmpty() throws Exception {
    ReplaceChecker checker = fromBeanClass(Replacer_IfaceEmpty.class);
    assertThat(checker.check(BeanEmpty.class)).isFalse();
  }
}