package kz.greetgo.depinject.mvc;

import java.lang.reflect.Method;
import java.util.List;

public class ControllerHandler extends TunnelHandlerList {
  private ControllerHandler() {
  }

  public static ControllerHandler create(Object controller, Views views) {
    final Builder builder = new Builder(controller, views);

    builder.build();

    return builder.result;
  }

  private static class Builder {
    final Object controller;
    final Views views;

    final ControllerHandler result = new ControllerHandler();

    final Class<?> controllerClass;

    public Builder(Object controller, Views views) {
      this.controller = controller;
      this.views = views;

      controllerClass = controller.getClass();
    }

    String parentMapping = "";

    void build() {
      prepareParentMapping();
      for (Method method : controllerClass.getMethods()) {
        appendHandlerForMethod(method);
      }
    }


    private void prepareParentMapping() {
      final Mapping mapping = controllerClass.getAnnotation(Mapping.class);
      if (mapping != null) parentMapping = mapping.value();
    }

    private void appendHandlerForMethod(final Method method) {
      final Mapping mapping = method.getAnnotation(Mapping.class);
      if (mapping == null) return;
      final TargetCatcher targetCatcher = new TargetCatcher(parentMapping + mapping.value());

      final List<MethodParamExtractor> extractorList = MethodParameterMeta.create(method);

      result.add(new TunnelHandler() {
        @Override
        public boolean handleTunnel(RequestTunnel tunnel) {
          CatchResult catchResult = targetCatcher.catchTarget(tunnel.getTarget());
          if (!catchResult.ok()) return false;

          try {

            MvcModel model=new MvcModel();
            Object[] paramValues = new Object[extractorList.size()];
            for (int i = 0, C = extractorList.size(); i < C; i++) {
              final MethodParamExtractor e = extractorList.get(i);
              paramValues[i] = e.extract(catchResult, tunnel, model);
            }

            final Object result = method.invoke(controller, paramValues);

            executeView(result, tunnel, catchResult, method);

            return true;

          } catch (Exception e) {
            views.errorView(tunnel.getResponseOutputStream(), tunnel.getTarget(), e);
            e.printStackTrace();
            return true;
          }
        }
      });
    }

    private void executeView(Object controllerMethodResult, RequestTunnel tunnel, CatchResult catchResult, Method method) {

    }

  }

}
