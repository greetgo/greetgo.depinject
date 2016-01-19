package kz.greetgo.depinject.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MethodParameterMeta {

  public static List<MethodParameterValueExtractor> create(Method method) {
    final List<MethodParameterValueExtractor> extractorList = new ArrayList<>();

    final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    final Type[] genericParameterTypes = method.getGenericParameterTypes();
    for (int i = 0, C = genericParameterTypes.length; i < C; i++) {
      final MethodParameterMeta methodParameterMeta = new MethodParameterMeta(
        i, method, genericParameterTypes[i], parameterAnnotations[i]
      );
      extractorList.add(methodParameterMeta.createExtractor());
    }

    return extractorList;
  }


  private final int parameterIndex;
  private final Method method;
  private final Type genericParameterType;
  private final Annotation[] parameterAnnotation;

  public MethodParameterMeta(int parameterIndex, Method method,
                             Type genericParameterType, Annotation[] parameterAnnotation) {
    this.parameterIndex = parameterIndex;
    this.method = method;
    this.genericParameterType = genericParameterType;
    this.parameterAnnotation = parameterAnnotation;
    prepareAnnotations();
  }

  private String parValue = null;

  private final void prepareAnnotations() {
    for (Annotation annotation : parameterAnnotation) {
      if (annotation instanceof Par) {
        parValue = ((Par) annotation).value();
        continue;
      }
    }
  }

  public MethodParameterValueExtractor createExtractor() {
    if (parValue != null) {
      return new MethodParameterValueExtractor() {
        @Override
        public Object extract(CatchResult catchResult, RequestTunnel tunnel) {
          final String[] paramValues = tunnel.getParamValues(parValue);
          return MvcUtil.convertStrsToType(paramValues, genericParameterType);
        }
      };
    }
    throw new RuntimeException("Cannot extract parameter value: parameterIndex: " + parameterIndex
      + "; method: " + method.toGenericString());
  }
}
