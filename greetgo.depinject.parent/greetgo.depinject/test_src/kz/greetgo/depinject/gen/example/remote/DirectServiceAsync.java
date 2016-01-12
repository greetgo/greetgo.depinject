package kz.greetgo.depinject.gen.example.remote;

import java.util.List;
import java.util.Map;

import kz.greetgo.depinject.src.gwtrpc.InvokeServiceAsync;

public interface DirectServiceAsync extends InvokeServiceAsync<String, Map<String, List<Long>>> {}
