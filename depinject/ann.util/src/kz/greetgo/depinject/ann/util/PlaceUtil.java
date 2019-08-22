package kz.greetgo.depinject.ann.util;

public class PlaceUtil {

  public static final Place NO_WHERE = new Place() {
    @Override
    public PlaceType type() {
      return PlaceType.NoWhere;
    }

    @Override
    public String display() {
      return "";
    }

    @Override
    public QualifierData qualifier() {
      return QualifierData.absent();
    }
  };

}
