package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans034.BeanConfig034;
import kz.greetgo.depinject.gen.test_beans034.MainBean034;
import kz.greetgo.depinject.gen.test_beans034.TargetBean034_1;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerManagerTest3 {

  @Include(BeanConfig034.class)
  interface BeanContainer034 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean034 mainBean();
  }

  @Test
  public void using_qualifier() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer034.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    BeanCreation mainBeanCreation = bcm.beanCreationList.stream()
        .filter(a -> a.beanClass == MainBean034.class)
        .findAny()
        .orElseThrow(() -> new RuntimeException("No bean of " + MainBean034.class));

    BeanGetterInPublicField beanGetter = mainBeanCreation.beanGetterInPublicFieldList.get(0);

    Class<?> actualTargetClass = beanGetter.beanReference.getterCreations.get(0).beanCreation.beanClass;
    assertThat(actualTargetClass.getName()).isEqualTo(TargetBean034_1.class.getName());
  }
}
