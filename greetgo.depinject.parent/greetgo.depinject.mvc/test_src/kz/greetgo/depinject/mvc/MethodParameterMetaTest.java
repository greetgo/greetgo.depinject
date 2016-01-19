package kz.greetgo.depinject.mvc;

import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static kz.greetgo.depinject.mvc.TestUtil.getMethod;
import static org.fest.assertions.api.Assertions.assertThat;

public class MethodParameterMetaTest {

  class ForStrRequestParam {
    public void forTest(@Par("strParam") String strParam) {
    }
  }

  @Test
  public void strRequestParam() throws Exception {
    final Method strRequestParam = getMethod(ForStrRequestParam.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(strRequestParam).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String paramValue = RND.str(10);

    tunnel.setParam("strParam", paramValue);

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isEqualTo(paramValue);
  }

  class ForLongRequestParam {
    public void forTest(@Par("param1") long param1, @Par("param2") Long param2) {
    }
  }

  @Test
  public void longRequestParam() throws Exception {
    final Method longRequestParam = getMethod(ForLongRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(longRequestParam);
    MethodParameterValueExtractor e1 = ee.get(0);
    MethodParameterValueExtractor e2 = ee.get(1);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.plusLong(1000000000);
    String param2 = "" + RND.plusLong(1000000000);

    tunnel.setParam("param1", param1);
    tunnel.setParam("param2", param2);

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(Long.valueOf(param1));
    assertThat(e2.extract(catchResult, tunnel)).isEqualTo(Long.valueOf(param2));

    tunnel.clearParam("param1");
    tunnel.clearParam("param2");

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(0L);
    assertThat(e2.extract(catchResult, tunnel)).isNull();

  }

  class ForIntRequestParam {
    public void forTest(@Par("param1") int param1, @Par("param2") Integer param2) {
    }
  }

  @Test
  public void intRequestParam() throws Exception {
    final Method longRequestParam = getMethod(ForIntRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(longRequestParam);
    MethodParameterValueExtractor e1 = ee.get(0);
    MethodParameterValueExtractor e2 = ee.get(1);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.plusInt(1000000000);
    String param2 = "" + RND.plusInt(1000000000);

    tunnel.setParam("param1", param1);
    tunnel.setParam("param2", param2);

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(Integer.valueOf(param1));
    assertThat(e2.extract(catchResult, tunnel)).isEqualTo(Integer.valueOf(param2));

    tunnel.clearParam("param1");
    tunnel.clearParam("param2");

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(0);
    assertThat(e2.extract(catchResult, tunnel)).isNull();

  }
}
