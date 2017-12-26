package kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

@Bean
@ReplaceWithAnn(Ann032.class)
public class Replacer032 extends AbstractReplacer032 {
  @Override
  protected void replaceBeanClass(String className) {
    Log032.logs032.add("Replacer032.replaceBeanClass " + className);
  }
}
