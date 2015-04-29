package kz.greetgo.depinject.gen.example.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kz.greetgo.depinject.src.gwtrpc.InvokeService;

public interface FakedService extends InvokeService<Map<String, List<Long>>, Date> {}
