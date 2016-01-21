package kz.greetgo.depinject.mvc;

public interface MethodParamExtractor {
  Object extract(CatchResult catchResult, RequestTunnel tunnel) throws Exception;
}
