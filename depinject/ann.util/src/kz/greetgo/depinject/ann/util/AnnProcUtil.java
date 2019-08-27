package kz.greetgo.depinject.ann.util;

import kz.greetgo.depinject.ann.util.message.CommonError;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnProcUtil {

  public static List<ExecutableElement> extractMethods(TypeElement typeElement) {

    Set<ExecutableElement> methods = new HashSet<>();

    TypeElement current = typeElement;

    while (current.getKind() == ElementKind.CLASS) {
      methods.addAll(
        current.getEnclosedElements()
          .stream()
          .filter(e -> e instanceof ExecutableElement)
          .map(e -> (ExecutableElement) e)
          .filter(e -> e.getKind() == ElementKind.METHOD)
          .filter(e -> !e.getModifiers().contains(javax.lang.model.element.Modifier.STATIC))
          .collect(Collectors.toSet())
      );

      TypeMirror superclass = current.getSuperclass();
      if (!(superclass instanceof DeclaredType)) {
        break;
      }

      DeclaredType superDeclaredType = (DeclaredType) superclass;

      Element element = superDeclaredType.asElement();

      if (!(element instanceof TypeElement)) {
        break;
      }

      if (Object.class.getName().equals(element.toString())) {
        break;
      }

      current = (TypeElement) element;
    }

    return methods
      .stream()
      .sorted(Comparator.comparing(x -> x.getSimpleName().toString()))
      .collect(Collectors.toList());

  }

  public static List<String> getMethodAnnotationQualifiedNames(ExecutableElement method) {
    List<String> ret = new ArrayList<>();
    for (AnnotationMirror annotationMirror : method.getAnnotationMirrors()) {
      Element element = annotationMirror.getAnnotationType().asElement();
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        ret.add(typeElement.getQualifiedName().toString());
      }
    }
    return ret;
  }

  public static Map<String, ValueEntry> getMethodAnnotationValues(ExecutableElement method,
                                                                  String annotationQualifiedName) {
    Map<String, ValueEntry> ret = new HashMap<>();

    for (AnnotationMirror annotationMirror : method.getAnnotationMirrors()) {
      Element element = annotationMirror.getAnnotationType().asElement();
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        if (typeElement.getQualifiedName().toString().equals(annotationQualifiedName)) {

          for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> e
            : annotationMirror.getElementValues().entrySet()) {

            String key = e.getKey().getSimpleName().toString();
            Object value = e.getValue().getValue();

            ret.put(key, new ValueEntry(value));
          }

        }
      }
    }

    return ret;
  }

  public static String toCode(TypeMirror typeMirror) {
    return typeMirror.toString();
  }

  public static String toCode(TypeElement typeElement) {
    return typeElement.toString();
  }

  public static boolean isList(DeclaredType declaredType) {
    Element element = declaredType.asElement();
    if (element instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) element;
      return "java.util.List".contentEquals(typeElement.getQualifiedName());
    }
    return false;
  }

  public static BeanRefData toBeanRefData(TypeMirror typeMirror, Place place) throws InternalError {
    DeclaredType declaredType = typeMirror.accept(GetDeclaredType.get(), null).orElse(null);

    if (declaredType == null) {
      throw new CommonError(place, "Unknown TypeMirror = " + typeMirror);
    }

    if (isList(declaredType)) {

      if (declaredType.getTypeArguments().isEmpty()) {
        throw new CommonError(place, "java.util.List has no type arguments");
      }
      if (declaredType.getTypeArguments().size() > 1) {
        throw new CommonError(place, "java.util.List has too many type arguments = "
          + declaredType.getTypeArguments().size());
      }

      TypeMirror argumentTypeMirror = declaredType.getTypeArguments().get(0);

      Optional<DeclaredType> argumentDeclaredTypeOptional = argumentTypeMirror.accept(GetDeclaredType.get(), null);

      DeclaredType argumentDeclaredType = argumentDeclaredTypeOptional.orElse(null);

      if (argumentDeclaredType == null) {
        throw new CommonError(place, "Unknown type of argument of java.util.List<> = " + argumentTypeMirror);
      }

      Element argumentElement = argumentDeclaredType.asElement();

      if (!(argumentElement instanceof TypeElement)) {
        throw new CommonError(place, "Unknown type element of argument of java.util.List<> = " + argumentElement);
      }

      TypeElement argumentTypeElement = (TypeElement) argumentElement;
      return new BeanRefData(argumentTypeElement, true);
    }

    {
      Element element = declaredType.asElement();

      if (!(element instanceof TypeElement)) {
        throw new CommonError(place, "Unknown type element " + element);
      }

      TypeElement typeElement = (TypeElement) element;

      return new BeanRefData(typeElement, false);
    }

  }

}
