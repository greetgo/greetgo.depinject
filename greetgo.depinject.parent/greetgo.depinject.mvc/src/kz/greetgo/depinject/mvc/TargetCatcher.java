package kz.greetgo.depinject.mvc;

public class TargetCatcher {
  private final String targetMapping;

  public TargetCatcher(String targetMapping) {
    this.targetMapping = targetMapping;
  }

  public CatchResult catchTarget(String target) {
    return null;
  }
}
