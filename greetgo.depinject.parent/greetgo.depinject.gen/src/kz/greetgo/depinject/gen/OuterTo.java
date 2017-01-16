package kz.greetgo.depinject.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class OuterTo {
  public static Outer file(File file) {
    try {
      FileOutputStream fileOut = new FileOutputStream(file);
      PrintStream out = new PrintStream(fileOut, false, "UTF-8");
      return new OuterToPrintStream("  ", out);
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
