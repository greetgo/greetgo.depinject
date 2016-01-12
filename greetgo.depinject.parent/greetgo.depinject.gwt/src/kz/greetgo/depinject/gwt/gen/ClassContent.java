package kz.greetgo.depinject.gwt.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContent {
  public String packageName, name;
  
  private final List<Class<?>> implementList = new ArrayList<Class<?>>();
  
  private final Map<String, String> importMap = new HashMap<>();
  
  public String imp(String fullClassName) {
    String[] split = fullClassName.split("\\.");
    String className = split[split.length - 1];
    String containsFullClassName = importMap.get(className);
    if (containsFullClassName != null) {
      if (containsFullClassName.equals(fullClassName)) return className;
      return fullClassName;
    }
    importMap.put(className, fullClassName);
    return className;
  }
  
  public String imp(Class<?> cl) {
    return imp(cl.getName());
  }
  
  public class PrintBlock {
    private PrintBlock() {}
    
    private final StringBuilder content = new StringBuilder();
    private int indent = 1;
    private int tabSize = 2;
    private boolean onNewLine = true;
    
    public void moreIndent() {
      indent++;
    }
    
    public void lessIndent() {
      indent--;
    }
    
    public PrintBlock pr(String str) {
      if (str == null) str = "";
      if (str.length() == 0) {
        linePart("");
        return this;
      }
      if ("\n".equals(str)) {
        content.append('\n');
        onNewLine = true;
        return this;
      }
      String[] split = str.split("\n");
      for (int i = 0, C = split.length; i < C; i++) {
        String line = split[i];
        linePart(line);
        if (i + 1 < C) {
          content.append('\n');
          onNewLine = true;
        }
        
        //        if (line.length() == 0 && i == C - 1) break;
        
      }
      
      return this;
    }
    
    private void linePart(String linePart) {
      if (onNewLine) {
        onNewLine = false;
        for (int i = 0, C = indent * tabSize; i < C; i++) {
          content.append(' ');
        }
      }
      content.append(linePart);
    }
    
    public PrintBlock prn(String str) {
      if (str != null) pr(str);
      return pr("\n");
    }
    
    public PrintBlock prn() {
      return prn(null);
    }
    
  }
  
  private final List<PrintBlock> printBlockList = new ArrayList<>();
  
  public PrintBlock newPrintBlock() {
    PrintBlock ret = new PrintBlock();
    printBlockList.add(ret);
    return ret;
  }
  
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder(10000);
    
    List<String> implList = new ArrayList<String>();
    
    for (Class<?> cl : implementList) {
      implList.add(imp(cl));
    }
    
    ret.append("package ").append(packageName).append(";\n\n");
    for (String fullClassName : importMap.values()) {
      ret.append("import ").append(fullClassName).append(";\n");
    }
    
    ret.append('\n');
    
    ret.append("public class ").append(name);
    if (implList.size() > 0) {
      boolean first = true;
      for (String cl : implList) {
        if (first) {
          first = false;
          ret.append(" implements ");
        } else {
          ret.append(", ");
        }
        ret.append(cl);
      }
    }
    ret.append(" {\n");
    
    for (PrintBlock pb : printBlockList) {
      ret.append(pb.content);
    }
    
    ret.append("}");
    
    return ret.toString();
  }
  
  public void addImplement(Class<?> cl) {
    implementList.add(cl);
  }
  
  public static void main(String[] args) {
    String s = "gfdsgdfs\ngfdsgf";
    
    String[] split = s.split("\n");
    for (String ss : split) {
      System.out.println("[[" + ss + "]]");
    }
  }
}
