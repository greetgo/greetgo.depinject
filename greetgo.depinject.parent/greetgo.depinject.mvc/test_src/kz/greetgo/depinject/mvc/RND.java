package kz.greetgo.depinject.mvc;

import java.util.Random;

public class RND {
  public static final String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
  public static final String RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

  public static final String eng = "abcdefghijklmnopqrstuvwxyz";
  public static final String ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static final String DEG = "0123456789";

  public static final String ALL = rus + RUS + eng + ENG + DEG;

  public static final Random rnd = new Random();

  public static String str(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = ALL.charAt(rnd.nextInt(ALL.length()));
    }
    return new String(charArray);
  }

  public static String intStr(int len) {
    char[] charArray = new char[len];
    for (int i = 0; i < len; i++) {
      charArray[i] = DEG.charAt(rnd.nextInt(DEG.length()));
    }
    return new String(charArray);
  }

  public static long plusLong(long max) {
    long L = rnd.nextLong();
    if (L < 0) L = -L;
    return L % max;
  }

  public static long plusInt(int max) {
    return rnd.nextInt(max);
  }
}
