package kz.greetgo.depinject.testng.tests.p001_the_first;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.BeanContainerGenerator;
import kz.greetgo.depinject.testng.test_beans_package.AbstractDepinjectTestNgTestBeans;
import kz.greetgo.depinject.testng.test_beans_package.for_include_by_str.OnSideBean;
import kz.greetgo.util.ServerUtil;
import org.testng.annotations.Test;

import java.io.File;
import java.io.PrintWriter;

import static kz.greetgo.util.ServerUtil.dummyCheck;

public class GenerationTest {

  @Include(AbstractDepinjectTestNgTestBeans.class)
  public interface TestBeanContainer extends BeanContainer {
    @SuppressWarnings("unused")
    OnSideBean getLeftBean();
  }

  @Test
  public void test() throws Exception {
    BeanContainerGenerator bcg = new BeanContainerGenerator();
    bcg.packageName = "asd";
    bcg.beanContainerInterface = TestBeanContainer.class;
    bcg.implClassName = "Asd";

    dummyCheck(new File("build/asd").mkdirs());
    try (final PrintWriter writer = new PrintWriter("build/asd/Asd.java", "UTF-8")) {
      bcg.writeTo(writer);
    }

  }
}
