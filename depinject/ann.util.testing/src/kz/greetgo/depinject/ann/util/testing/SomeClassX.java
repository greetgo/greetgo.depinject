package kz.greetgo.depinject.ann.util.testing;

import kz.greetgo.depinject.ann.util.testing.model.SomeModel1;

import java.util.List;

@SuppressWarnings("unused")
@ExampleAnnotation(name = "Name of some class X")
public class SomeClassX {

  @ExampleAnnotation(name = "Name of some method X")
  @Cool(name = "Coll name", status = "Cool status", sun = "Sun is lighting forever")
  public void someMethodX() {

    System.out.println("Some Method");

  }

  public List<SomeModel1> returnsListSomeModel1() {
    return null;
  }

  public List<SomeModel1.SomeModel2> returnsListSomeModel2() {
    return null;
  }

  public SomeModel1 returnsSomeModel1() {
    return null;
  }

  public SomeModel1.SomeModel2 returnsSomeModel2() {
    return null;
  }

}
