package kz.greetgo.depinject.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static kz.greetgo.util.ServerUtil.dummyCheck;

public abstract class AbstractDepinjectTestNg {

  String srcDir = null;

  @BeforeClass
  public void prepareDepinject() throws Exception {
    beforeGenerateTestSources();
    srcDir = DepinjectTestNg.prepareDepinjectTestNg(this, generateSrcTempDir());
  }

  protected void beforeGenerateTestSources() {
    //override it
  }

  @AfterClass
  public void removeSrcDir() {
    if (!needToRemoveSrcDir()) {
      return;
    }

    if (srcDir == null) {
      return;
    }

    removeRecursively(new File(srcDir));
  }

  private static void removeRecursively(File file) {
    if (!file.exists()) {
      return;
    }

    if (file.isDirectory()) {
      final File[] files = file.listFiles();
      if (files != null) {
        for (File subFile : files) {
          removeRecursively(subFile);
        }
      }
    }

    dummyCheck(file.delete());
  }

  protected boolean needToRemoveSrcDir() {
    return true;
  }

  protected String generateSrcTempDir() {

    //gradle infrastructure
    if (new File("build").isDirectory()) {
      return "build/depinject_testNg";
    }

    //maven2 infrastructure
    if (new File("target").isDirectory()) {
      return "target/depinject_testNg";
    }

    //unknown infrastructure
    {
      //noinspection SpellCheckingInspection
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
      return System.getProperty("java.io.tmpdir") + "/depinject_testNg_" + sdf.format(new Date());
    }
  }
}
