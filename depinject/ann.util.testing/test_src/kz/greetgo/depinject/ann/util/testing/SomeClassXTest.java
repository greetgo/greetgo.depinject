package kz.greetgo.depinject.ann.util.testing;

import org.fest.assertions.data.MapEntry;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class SomeClassXTest {

  @Test
  public void testHelloWorld() {
    TestResults testResults = ExampleAnnProcessor.newTestResults();

    String hello = testResults.hello();
    assertThat(hello).isEqualTo(ExampleAnnProcessor.HI_WORLD);
  }

  @Test
  public void readAnnotationFromSomeMethodX() {

    TestResults testResults = ExampleAnnProcessor.newTestResults();

    Map<String, Map<String, String>> stringMapMap = testResults.readAnnotationsFromSomeMethodX();

    {
      Map<String, String> values = stringMapMap.get(ExampleAnnotation.class.getName());
      assertThat(values).isNotNull();
      assertThat(values).contains(MapEntry.entry("name", "Name of some method X"));
    }
    {
      Map<String, String> values = stringMapMap.get(Cool.class.getName());
      assertThat(values).isNotNull();
      assertThat(values).contains(MapEntry.entry("name", "Coll name"));
      assertThat(values).contains(MapEntry.entry("sun", "Sun is lighting forever"));
      assertThat(values).contains(MapEntry.entry("status", "Cool status"));
    }

  }

  @Test
  public void toCode_ListOfSomeModel1() {

    TestResults testResults = ExampleAnnProcessor.newTestResults();

    String str = testResults.returnsListSomeModel1_toCode();

    String listQualifiedName = List.class.getName();
    String someModel1QualifiedName = "kz.greetgo.depinject.ann.util.testing.model.SomeModel1";

    assertThat(str).isEqualTo(listQualifiedName + "<" + someModel1QualifiedName + ">");

  }

  @Test
  public void toCode_ListOfSomeModel2() {

    TestResults testResults = ExampleAnnProcessor.newTestResults();

    String str = testResults.returnsListSomeModel2_toCode();

    String listQualifiedName = List.class.getName();
    String someModel2QualifiedName = "kz.greetgo.depinject.ann.util.testing.model.SomeModel1.SomeModel2";

    assertThat(str).isEqualTo(listQualifiedName + "<" + someModel2QualifiedName + ">");

  }

  @Test
  public void toCode_SomeModel1() {

    TestResults testResults = ExampleAnnProcessor.newTestResults();

    String str = testResults.returnsSomeModel1_toCode();

    String someModel1QualifiedName = "kz.greetgo.depinject.ann.util.testing.model.SomeModel1";

    assertThat(str).isEqualTo(someModel1QualifiedName);

  }

  @Test
  public void toCode_SomeModel2() {

    TestResults testResults = ExampleAnnProcessor.newTestResults();

    String str = testResults.returnsSomeModel2_toCode();

    String someModel2QualifiedName = "kz.greetgo.depinject.ann.util.testing.model.SomeModel1.SomeModel2";

    assertThat(str).isEqualTo(someModel2QualifiedName);

  }

  public static void main(String[] args) {
    TestResults testResults = ExampleAnnProcessor.newTestResults();
    System.out.println(testResults.readAnnotationsFromSomeMethodX());
  }

}
