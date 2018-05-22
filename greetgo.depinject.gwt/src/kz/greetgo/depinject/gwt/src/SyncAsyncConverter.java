package kz.greetgo.depinject.gwt.src;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SyncAsyncConverter {
  <ToServer, FromServer>
  Request convertInvoking(ToServer toServer,
                          AsyncCallback<FromServer> callback,
                          InvokeService<ToServer, FromServer> sync);
}
