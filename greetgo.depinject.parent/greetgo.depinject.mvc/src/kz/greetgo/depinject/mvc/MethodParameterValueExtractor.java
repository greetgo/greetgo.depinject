package kz.greetgo.depinject.mvc;

public interface MethodParameterValueExtractor {
  Object extract(CatchResult catchResult, RequestTunnel tunnel);
}
