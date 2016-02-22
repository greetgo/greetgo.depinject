package kz.greetgo.depinject.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;

import static kz.greetgo.util.ServerUtil.dummyCheck;

public abstract class AbstractDepinjectTestNg {

  String srcDir = null;

  @BeforeClass
  public void prepareDepinject() throws Exception {
    beforeGenerateTestSources();
    srcDir = DepinjectTestNg.prepareDepinjectTestNg(this, getSrcTempDir());
  }

  protected void beforeGenerateTestSources() throws Exception {
    //override it
  }

  @AfterClass
  public void removeSrcDir() throws Exception {
    if (!needToRemoveSrcDir()) return;
    if (srcDir == null) return;

    removeRecursively(new File(srcDir));
  }

  private static void removeRecursively(File file) {
    if (!file.exists()) return;

    if (file.isDirectory()) {
      final File[] files = file.listFiles();
      if (files != null) for (File subFile : files) {
        removeRecursively(subFile);
      }
    }

    dummyCheck(file.delete());
  }

  protected boolean needToRemoveSrcDir() {
    return true;
  }

  protected String getSrcTempDir() {
    return System.getProperty("java.io.tmpdir") + "/depinject_testNg";
  }

}
