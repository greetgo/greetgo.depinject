package kz.greetgo.depinject.src.gwtrpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SyncAsyncConverter {
  public <ToServer, FromServer> Request convertInvoking(final ToServer toServer,
      final AsyncCallback<FromServer> callback, InvokeService<ToServer, FromServer> sync);
}
