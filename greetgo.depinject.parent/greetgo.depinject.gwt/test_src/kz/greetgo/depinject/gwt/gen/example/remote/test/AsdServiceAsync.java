package kz.greetgo.depinject.gwt.gen.example.remote.test;

import kz.greetgo.depinject.gwt.gen.example.remote.DirectServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AsdServiceAsync extends DirectServiceAsync {
  void afterInject(AsyncCallback<Void> callback);
}
