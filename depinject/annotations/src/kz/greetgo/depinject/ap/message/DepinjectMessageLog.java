package kz.greetgo.depinject.ap.message;

import kz.greetgo.depinject.ann.util.message.Message;
import kz.greetgo.depinject.ann.util.message.MessageLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepinjectMessageLog {

  public static List<Message> __getMessagesFor__(Class aClass) {
    List<Message> list = QualifiedNameToMessageMap.get(aClass.getName());

    List<Message> ret = new ArrayList<>();
    if (list != null) {
      ret.addAll(list);
    }
    return ret;
  }

  private static final Map<String, List<Message>> QualifiedNameToMessageMap = new HashMap<>();

  public static void appendMessage(String qualifiedName, Message message) {
    QualifiedNameToMessageMap
      .computeIfAbsent(qualifiedName, qn -> new ArrayList<>())
      .add(message)
    ;
  }

  public static boolean hasErrors(String qualifiedName) {
    List<Message> list = QualifiedNameToMessageMap.get(qualifiedName);

    if (list == null) {
      return false;
    }

    for (Message message : list) {
      if (message.getLevel() == MessageLevel.ERROR) {
        return true;
      }
    }

    return false;
  }

  public static void throwErrorIfNeed(String qualifiedName) {
    List<Message> list = QualifiedNameToMessageMap.get(qualifiedName);
    if (list == null) {
      return;
    }
    if (list.isEmpty()) {
      return;
    }

    throw list.get(0);
  }
}
