package kz.greetgo.depinject.gen;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import kz.greetgo.depinject.src.gwtrpc.InvokeService;

public class GoingTypes {
  public final Type toServer, fromServer;
  
  private GoingTypes(Type toServer, Type fromServer) {
    this.toServer = toServer;
    this.fromServer = fromServer;
  }
  
  public static GoingTypes extractFromSync(Class<?> syncClass) {
    if (!InvokeService.class.isAssignableFrom(syncClass)) {
      throw new IllegalArgumentException(syncClass + " is not assignable to "
          + InvokeService.class.getSimpleName());
    }
    
    for (Type type : syncClass.getGenericInterfaces()) {
      GoingTypes ret = findInType(type);
      if (ret != null) return ret;
    }
    
    throw new IllegalArgumentException("Ooops");
  }
  
  private static GoingTypes findInType(Type type) {
    if (type instanceof Class) {
      Class<?> cl = (Class<?>)type;
      for (Type subtype : cl.getGenericInterfaces()) {
        GoingTypes ret = findInType(subtype);
        if (ret != null) return ret;
      }
      
      return null;
    }
    ParameterizedType ptype = (ParameterizedType)type;
    
    if (ptype.getRawType() == InvokeService.class) {
      Type[] args = ptype.getActualTypeArguments();
      return new GoingTypes(args[0], args[1]);
    }
    
    for (Type subtype : ((Class<?>)ptype.getRawType()).getGenericInterfaces()) {
      GoingTypes ret = findInType(subtype);
      if (ret != null) return ret;
    }
    
    return null;
  }
}
