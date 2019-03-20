package kz.greetgo.depinject.gen.scripts;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.util.RND;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kz.greetgo.util.ServerUtil.streamToStr;

public class SpringToDepinject {
  public static void main(String[] args) throws Exception {

    new SpringToDepinject().run();
  }

  private static final int RGO = Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS
    | Pattern.UNICODE_CASE | Pattern.COMMENTS;

  private static final Pattern P = Pattern.compile(
    "@Autowired(\\s+(private|protected|public))?\\s+(.+)\\s+(\\w+)\\s*;",
    RGO);

  private void run() throws Exception {
    String appDir = "/home/pompei/IdeaProjects/kaspiptp/kaspiptp.parent/";

    scanDirAndConvertSpringsFiles(new File(appDir + "kaspiptp.db/src/main/java/"));
    scanDirAndConvertSpringsFiles(new File(appDir + "kaspiptp.db/src/test/java/"));
    scanDirAndConvertSpringsFiles(new File(appDir + "kaspiptp.server/src/main/java/"));
    scanDirAndConvertSpringsFiles(new File(appDir + "kaspiptp.client/src/main/java/"));
    scanDirAndConvertSpringsFiles(new File(appDir + "kaspiptp.stand/src/main/java/"));

    //noinspection unused,SpellCheckingInspection
    File file = new File("/home/pompei/IdeaProjects/kaspiptp/kaspiptp.parent/kaspiptp.db/src/main/java/" +
      "kz/greetgo/kaspiptp/db/register/impl/UtilRegisterImpl.java");

    //convertSpringFile(file);

    //System.out.println(res);
    System.out.println("COMPLETE");
  }

  private void scanDirAndConvertSpringsFiles(File dir) throws Exception {
    final File[] subs = dir.listFiles();
    if (subs == null) { return; }
    for (File sub : subs) {
      if (sub.isDirectory()) {
        scanDirAndConvertSpringsFiles(sub);
        continue;
      }
      if (sub.isFile() && sub.getName().endsWith(".java")) {
        try {
          convertSpringFile(sub);
        } catch (Exception e) {
          throw new Exception("Converting file " + sub, e);
        }
      }
    }
  }

  private void convertSpringFile(File file) throws Exception {
    final String text = streamToStr(new FileInputStream(file));

    final Matcher matcher = P.matcher(text);

    StringBuffer sb = new StringBuffer();

    final List<String> varList = new ArrayList<>();

    String tmp = RND.strInt(10);

    while (matcher.find()) {
      varList.add(matcher.group(4));
      matcher.appendReplacement(sb, "public BeanGetter<$3> $4" + tmp + ";");
    }
    matcher.appendTail(sb);

    String res = sb.toString();

    final String vars;
    {
      sb.setLength(0);
      for (String var : varList) {
        //if (var.length() > 2) {
        sb.append(var).append('|');
        //}
      }
      if (sb.length() > 0) { sb.setLength(sb.length() - 1); }
      vars = sb.toString();
    }

    if (vars.length() > 0) {
      sb.setLength(0);
      for (String line : res.split("\n")) {

        if (line.startsWith("import ")) {
          sb.append(line).append("\n");
          continue;
        }

        if (line.matches(".*\\b(" + vars + ")\\b\\(.*")) {
          sb.append(line).append("\n");
          continue;
        }

        sb.append(line.replaceAll("\\b(" + vars + ")\\b", "$1.get()")).append("\n");
      }
      res = sb.toString();
    }
    res = res.replaceAll(tmp, "");

    if (varList.size() > 0) {
      res = res.replaceFirst(";", ";\nimport " + BeanGetter.class.getName() + ";");
    }

    if (res.contains("import org.springframework.stereotype.Component;")) {
      res = res.replaceFirst("import org.springframework.stereotype.Component;\n", "");
      res = res.replaceFirst("@Component", "@Bean");
      res = res.replaceFirst(";", ";\nimport " + Bean.class.getName() + ";");
    }
    if (res.contains("import org.springframework.context.annotation.Configuration;")) {
      res = res.replaceFirst("import org.springframework.context.annotation.Configuration;\n", "");
      res = res.replaceFirst("@Configuration", "@BeanConfig");
      res = res.replaceFirst(";", ";\nimport " + BeanConfig.class.getName() + ";");
    }
    if (res.contains("import org.springframework.context.annotation.ComponentScan;")) {
      res = res.replaceFirst("import org.springframework.context.annotation.ComponentScan;\n", "");
      res = res.replaceFirst("@ComponentScan", "@BeanScanner");
      res = res.replaceFirst(";", ";\nimport " + BeanScanner.class.getName() + ";");
    }
    if (res.contains("import org.springframework.transaction.annotation.Transactional;")) {
      res = res.replaceFirst("import org.springframework.transaction.annotation.Transactional;\n", "");
      res = res.replaceFirst("@Transactional", "@InTransaction");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.db.InTransaction;");
    }
    if (res.contains("import org.springframework.context.annotation.Import;")) {
      res = res.replaceFirst("import org.springframework.context.annotation.Import;\n", "");
      res = res.replaceFirst("@Import", "@Include");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.depinject.core.Include;");
    }
    if (res.contains("import org.springframework.test.context.ContextConfiguration;")) {
      res = res.replaceFirst("import org.springframework.test.context.ContextConfiguration;\n", "");
      res = res.replaceFirst("@ContextConfiguration\\(classes = ", "@ContainerConfig(");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.depinject.testng.ContainerConfig;");
    }
    if (res.contains("import org.springframework.web.bind.annotation.PathVariable;")) {
      res = res.replaceFirst("import org.springframework.web.bind.annotation.PathVariable;\n", "");
      res = res.replaceFirst("@PathVariable\\(", "@PathPar(");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.mvc.annotations.PathPar;");
    }
    if (res.contains("import org.springframework.web.bind.annotation.RequestMapping;")) {
      res = res.replaceFirst("import org.springframework.web.bind.annotation.RequestMapping;\n", "");
      res = res.replaceFirst("@RequestMapping\\(", "@Mapping(");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.mvc.annotations.Mapping;");
    }
    if (res.contains("import org.springframework.web.bind.annotation.RequestParam;")) {
      res = res.replaceFirst("import org.springframework.web.bind.annotation.RequestParam;\n", "");
      res = res.replaceFirst("@RequestParam\\(", "@Par(");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.mvc.annotations.Par;");
    }
    if (res.contains("import kz.greetgo.depinject.src.ScanBeans;")) {
      res = res.replaceFirst("import kz.greetgo.depinject.src.ScanBeans;\n", "");
      res = res.replaceFirst("@ScanBeans", "@BeanScanner");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.depinject.core.BeanScanner;");
    }

    //import org.springframework.web.bind.annotation.PathVariable;

    {
      res = res.replaceFirst("import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;",
        "import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;");
      res = res.replaceFirst("extends AbstractTestNGSpringContextTests", "extends AbstractDepinjectTestNg");
    }

    //res = res.replaceAll("import\\s+org.springframework\\S*\\n", "");
    res = res.replaceAll("import\\s+kz.greetgo.depinject.src.gwtrpc.ClientException;",
      "import kz.greetgo.depinject.gwt.src.ClientException;");

    if (res.contains("JdbcTemplate")) {
      res = res.replaceFirst(";", ";\nimport kz.greetgo.kaspiptp.db.beans.all.JdbcOper;");
      res = res.replaceFirst(";", ";\nimport kz.greetgo.db.ConnectionCallback;");
      res = res.replaceFirst("import org.springframework.jdbc.core.JdbcTemplate;\n", "");
      res = res.replaceAll("\\bJdbcTemplate\\b", "JdbcOper");
    }

    res = res.replaceFirst("import org.springframework.beans.factory.annotation.Autowired;\n", "");
    res = res.replaceFirst("import org.springframework.dao.DataAccessException;\n", "");
    res = res.replaceFirst("import org.springframework.context.annotation.Bean;\n", "");

    res = res.replaceFirst(
      "import org.springframework.jdbc.core.ConnectionCallback;",
      "import kz.greetgo.db.ConnectionCallback;"
    ).replaceAll("throws\\s+SQLException\\s*,\\s*DataAccessException", "throws Exception");

    res = res.replaceAll("import kz.greetgo.depinject.src.Bean;", "import kz.greetgo.depinject.core.Bean;");
    res = res.replaceAll("import kz.greetgo.depinject.src.BeanGetter;", "import kz.greetgo.depinject.core.BeanGetter;");

    res = res.replaceAll("import kz.greetgo.depinject.src.gwtrpc.InvokeServiceAsync;",
      "import kz.greetgo.depinject.gwt.src.InvokeServiceAsync;");
    res = res.replaceAll("import kz.greetgo.depinject.src.gwtrpc.InvokeService;",
      "import kz.greetgo.depinject.gwt.src.InvokeService;");
    res = res.replaceAll("import kz.greetgo.depinject.src.BeanConfig;", "import kz.greetgo.depinject.core.BeanConfig;");

    res = res.replaceAll("import kz.greetgo.depinject.src.Include;", "import kz.greetgo.depinject.core.Include;");
    res = res.replaceAll("import kz.greetgo.depinject.src.BeanContainer;",
      "import kz.greetgo.depinject.core.BeanContainer;");

    res = res.replaceAll("import kz.greetgo.depinject.src.HasAfterInject;",
      "import kz.greetgo.depinject.core.HasAfterInject;");

    res = res.replaceAll("import kz.greetgo.depinject.src.gwtrpc.SyncAsyncConverterDefault;",
      "import kz.greetgo.depinject.gwt.src.SyncAsyncConverterDefault;");

    try (final PrintStream out = new PrintStream(file, "UTF-8")) {
      out.print(res);
    }
    System.out.println("ok " + file);
  }
}
