package kz.greetgo.depinject.ann.util.testing;

import java.util.Map;

public interface TestResults {

  String hello();

  Map<String, Map<String, String>> readAnnotationsFromSomeMethodX();

  String returnsListSomeModel1_toCode();

  String returnsListSomeModel2_toCode();

  String returnsSomeModel1_toCode();

  String returnsSomeModel2_toCode();

}
