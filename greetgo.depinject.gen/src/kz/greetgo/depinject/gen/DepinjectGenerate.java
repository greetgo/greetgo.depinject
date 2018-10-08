package kz.greetgo.depinject.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static kz.greetgo.depinject.gen.DepinjectUtil.generateBeanContainersSources;
import static kz.greetgo.depinject.gen.DepinjectUtil.implementBeanContainers;

public class DepinjectGenerate {
  public static void main(String[] args) {
    System.exit(new DepinjectGenerate().exec(args));
  }

  private int exec(String[] args) {
    if (args.length == 0) {
      help();
      return 1;
    }

    if ("impl".equals(args[0])) {
      impl(args, 1);
      return 0;
    }

    System.err.println("ERROR: Unknown command: " + args[0]);
    help();
    return 1;
  }

  public static final String RESET = "\033[0m";  // Text Reset

  public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

  private void help() {
    System.err.println("    ");
    System.err.println("Using: java ... " + getClass().getName()
      + " impl -p <package_name1>[:<package_name2>[:...]] -s <out src dir> [-c]");
    System.err.println("    ");
    System.err.println("      Scan <package_name1>, <package_name2>, ... for interfaces extends BeanContainer and generate its implementation.");
    System.err.println("    Place generated implementations into <err src dir>. Package of implementation is same");
    System.err.println("    as implementing interface.");
    System.err.println("    ");
    System.err.println("    " + BLACK_BOLD + "-c" + RESET
      + " if specified this flag, then generated source would be compiled in <out src dir>");
  }

  private static class Arguments {
    List<String> packageNameList = new ArrayList<>();
    String outSrcDir;
    boolean compile;

    void parse(String[] args, int startFrom) {
      int i = startFrom;
      while (i < args.length) {
        String x = args[i];
        if ("-c".equals(x)) {
          compile = true;
          i++;
          continue;
        }
        if ("-p".equals(x)) {
          if (args.length == i + 1) {
            throw new RuntimeException("Please specify argument for parameter -p");
          }
          packageNameList = Arrays.stream(args[i + 1].split(":")).collect(Collectors.toList());
          i += 2;
          continue;
        }
        if ("-s".equals(x)) {
          if (args.length == i + 1) {
            throw new RuntimeException("Please specify argument for parameter -s");
          }
          outSrcDir = args[i + 1];
          i += 2;
          continue;
        }
        throw new RuntimeException("Unknown option " + x);
      }

      if (packageNameList == null || packageNameList.isEmpty()) {
        throw new RuntimeException("Please specify parameter -p");
      }
      if (outSrcDir == null) {
        throw new RuntimeException("Please specify parameter -s");
      }
    }

    @Override
    public String toString() {
      return "Parameters{" +
        "packageNameList='" + String.join(":", packageNameList) + '\'' +
        ", outSrcDir='" + outSrcDir + '\'' +
        ", compile=" + compile +
        '}';
    }
  }

  private void impl(String[] args, @SuppressWarnings("SameParameterValue") int startFrom) {
    Arguments arguments = new Arguments();
    arguments.parse(args, startFrom);

    if (arguments.compile) {
      arguments.packageNameList.forEach(pn -> implementBeanContainers(pn, arguments.outSrcDir));
      return;
    }

    {
      arguments.packageNameList.forEach(pn -> generateBeanContainersSources(pn, arguments.outSrcDir));
      return;
    }
  }
}
