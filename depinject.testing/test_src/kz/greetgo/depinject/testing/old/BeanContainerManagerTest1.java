package kz.greetgo.depinject.testing.old;

import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.ap.message.ManyCandidates;
import kz.greetgo.depinject.ap.message.Message;
import kz.greetgo.depinject.ap.message.NoCandidates;
import kz.greetgo.depinject.testing.old.t00x.test_beans007.BeanConfig007;
import kz.greetgo.depinject.testing.old.t00x.test_beans007.SomeBeanClass;
import org.testng.annotations.Test;

import java.util.List;

import static kz.greetgo.depinject.ap.message.__DepinjectMessageContainer__.__getMessagesFor__;
import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerManagerTest1 {

  @BeanContainer
  @Include(BeanConfig007.class)
  public interface For_prepareToWrite_ManyCandidates {
    @SuppressWarnings("unused")
    SomeBeanClass asd();
  }


  @Test
  public void prepareToWrite_ManyCandidates() {

    List<Message> list = __getMessagesFor__(For_prepareToWrite_ManyCandidates.class);
    assertThat(list).hasSize(1);
    assertThat(list.get(0)).isInstanceOf(ManyCandidates.class);

  }

  static class SomeLeftClass {}

  @BeanContainer
  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoCandidates {
    @SuppressWarnings("unused")
    SomeLeftClass asd();
  }

  @Test
  public void prepareToWrite_NoCandidates() {

    List<Message> list = __getMessagesFor__(For_prepareToWrite_NoCandidates.class);
    assertThat(list).hasSize(1);
    assertThat(list.get(0)).isInstanceOf(NoCandidates.class);

  }

  @SuppressWarnings("unused")
  @BeanContainer
  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed {}

  @Test
  public void prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed() {

    List<Message> list = __getMessagesFor__(For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed.class);
    assertThat(list).isEmpty();

  }
}
