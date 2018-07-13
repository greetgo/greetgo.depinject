package kz.greetgo.malina;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;

public class GenerateSrc {
  public static void main(String[] args) throws Exception {
    new GenerateSrc().exec(Paths.get(args[0]));
  }

  private void exec(Path dirTo) throws Exception {

    final File sinJava = dirTo.resolve("kz/greetgo/malina_gen/Sin.java").toFile();
    sinJava.getParentFile().mkdirs();

    List<String> lines = new ArrayList<>();
    lines.add("package kz.greetgo.malina_gen;");
    lines.add("public class Sin {");
    lines.add("  @Override");
    lines.add("  public String toString() {");
    lines.add("    return \"hello from Sin\";");
    lines.add("  }");
    lines.add("}");

    Files.write(sinJava.toPath(), lines, StandardCharsets.UTF_8, CREATE);

  }
}