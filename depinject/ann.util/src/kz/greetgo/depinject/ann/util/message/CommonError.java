package kz.greetgo.depinject.ann.util.message;

import kz.greetgo.depinject.ann.util.Place;
import kz.greetgo.depinject.ann.util.PlaceUtil;

public class CommonError extends Message {
  public final Place place;

  public CommonError(String message) {
    this(PlaceUtil.NO_WHERE, message);
  }

  public CommonError(Place place, String message) {
    super(place.display() + ":" + message);
    this.place = place;
  }

  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
