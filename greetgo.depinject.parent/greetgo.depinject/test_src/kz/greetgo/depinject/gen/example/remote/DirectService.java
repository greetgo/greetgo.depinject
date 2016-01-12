package kz.greetgo.depinject.gen.example.remote;

import java.util.List;
import java.util.Map;

import kz.greetgo.depinject.src.gwtrpc.InvokeService;

public interface DirectService extends InvokeService<String, Map<String, List<Long>>> {}
