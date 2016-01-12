package kz.greetgo.depinject.gen.example.remote.test;

import kz.greetgo.depinject.gen.example.remote.DirectServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AsdServiceAsync extends DirectServiceAsync {
  void afterInject(AsyncCallback<Void> callback);
}
