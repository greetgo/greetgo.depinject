package kz.greetgo.depinject.gen.example.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kz.greetgo.depinject.src.gwtrpc.InvokeServiceAsync;

public interface FakedServiceAsync extends InvokeServiceAsync<Map<String, List<Long>>, Date> {}
