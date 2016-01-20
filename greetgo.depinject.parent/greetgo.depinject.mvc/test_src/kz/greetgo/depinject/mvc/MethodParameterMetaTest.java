package kz.greetgo.depinject.mvc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static kz.greetgo.depinject.mvc.TestUtil.getMethod;
import static org.fest.assertions.api.Assertions.assertThat;

public class MethodParameterMetaTest {

  class ForStrRequestParam {
    public void forTest(@Par("strParam") String strParam) {
    }
  }

  @Test
  public void strRequestParam() throws Exception {
    final Method method = getMethod(ForStrRequestParam.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String paramValue = RND.str(10);

    tunnel.setParam("strParam", paramValue, "left value");

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isEqualTo(paramValue);
  }

  class ForLongRequestParam {
    public void forTest(@Par("param1") long param1, @Par("param2") Long param2) {
    }
  }

  @Test
  public void longRequestParam() throws Exception {
    final Method method = getMethod(ForLongRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(method);
    MethodParameterValueExtractor e1 = ee.get(0);
    MethodParameterValueExtractor e2 = ee.get(1);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.plusLong(1000000000);
    String param2 = "" + RND.plusLong(1000000000);

    tunnel.setParam("param1", param1, "left value 1");
    tunnel.setParam("param2", param2, "left value 2");

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
    final Method method = getMethod(ForIntRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(method);
    MethodParameterValueExtractor e1 = ee.get(0);
    MethodParameterValueExtractor e2 = ee.get(1);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.plusInt(1000000000);
    String param2 = "" + RND.plusInt(1000000000);

    tunnel.setParam("param1", param1, "left value 1");
    tunnel.setParam("param2", param2, "left value 2");

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(Integer.valueOf(param1));
    assertThat(e2.extract(catchResult, tunnel)).isEqualTo(Integer.valueOf(param2));

    tunnel.clearParam("param1");
    tunnel.clearParam("param2");

    assertThat(e1.extract(catchResult, tunnel)).isEqualTo(0);
    assertThat(e2.extract(catchResult, tunnel)).isNull();

  }

  @DataProvider
  public Object[][] simpleDateFormats() {
    return new Object[][]{

      new Object[]{"yyyy-MM-dd HH:mm:ss"},
      new Object[]{"dd.MM.yyyy HH:mm:ss"},
      new Object[]{"dd/MM/yyyy HH:mm:ss"},
      new Object[]{"yyyy-MM-dd HH:mm"},
      new Object[]{"dd.MM.yyyy HH:mm"},
      new Object[]{"dd/MM/yyyy HH:mm"},
      new Object[]{"yyyy-MM-dd"},
      new Object[]{"dd.MM.yyyy"},
      new Object[]{"dd/MM/yyyy"},

    };
  }

  class ForDateRequestParam {
    public void forTest(@Par("param") Date param) {
    }
  }

  @Test(dataProvider = "simpleDateFormats")
  public void dateRequestParam(String sdfFormat) throws Exception {
    final Method method = getMethod(ForDateRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(method);
    MethodParameterValueExtractor e1 = ee.get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    final Date date = RND.dateYears(-100, 0);
    final SimpleDateFormat sdf = new SimpleDateFormat(sdfFormat);
    final String dateStr = sdf.format(date);

    tunnel.setParam("param", dateStr, "left value");

    final Object actualDate = e1.extract(catchResult, tunnel);
    assertThat(actualDate).isInstanceOf(Date.class);
    assertThat(sdf.format(actualDate)).isEqualTo(dateStr);

    tunnel.clearParam("param");

    assertThat(e1.extract(catchResult, tunnel)).isNull();

  }

  class ForStrListRequestParam {
    public void forTest(@Par("param") List<String> param) {
    }
  }

  @Test
  public void listRequestParam() throws Exception {
    final Method method = getMethod(ForStrListRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(method);
    MethodParameterValueExtractor e = ee.get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.str(10);
    String param2 = "" + RND.str(10);

    tunnel.setParam("param", param1, param2);

    {
      final Object actual = e.extract(catchResult, tunnel);
      assertThat(actual).isInstanceOf(List.class);
      assertThat((List<String>) actual).containsExactly(param1, param2);
    }

    tunnel.clearParam("param");

    {
      final Object actual = e.extract(catchResult, tunnel);
      assertThat(actual).isInstanceOf(List.class);
      assertThat((List) actual).isEmpty();
    }
  }

  class ForStrSetRequestParam {
    public void forTest(@Par("param") Set<String> param) {
    }
  }

  @Test
  public void setRequestParam() throws Exception {
    final Method method = getMethod(ForStrSetRequestParam.class, "forTest");

    final List<MethodParameterValueExtractor> ee = MethodParameterMeta.create(method);
    MethodParameterValueExtractor e = ee.get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String param1 = "" + RND.str(10);
    String param2 = "" + RND.str(10);

    tunnel.setParam("param", param1, param2);

    {
      final Object actual = e.extract(catchResult, tunnel);
      assertThat(actual).isInstanceOf(Set.class);
      assertThat((Set<String>) actual).containsOnly(param1, param2);
    }

    tunnel.clearParam("param");

    {
      final Object actual = e.extract(catchResult, tunnel);
      assertThat(actual).isInstanceOf(Set.class);
      assertThat((Set) actual).isEmpty();
    }
  }

  class ForStrPathParam {
    public void forTest(@PathPar("param") String param) {
    }
  }

  @Test
  public void strPathParam() throws Exception {
    final Method method = getMethod(ForStrPathParam.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();

    String paramValue = RND.str(10);

    catchResult.setParam("param", paramValue);

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isEqualTo(paramValue);
  }

  class ForRequestInput_String {
    public void forTest(@RequestInput String content) {
    }
  }

  @Test
  public void requestInput_String() throws Exception {
    final Method method = getMethod(ForRequestInput_String.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();
    tunnel.forGetRequestReader = RND.str(10);

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isInstanceOf(String.class);
    assertThat(actualParamValue).isEqualTo(tunnel.forGetRequestReader);
  }

  class ForRequestInput_StringList {
    public void forTest(@RequestInput List<String> contentLines) {
    }
  }

  @Test
  public void requestInput_StringList() throws Exception {
    final Method method = getMethod(ForRequestInput_StringList.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    String line1 = RND.str(10);
    String line2 = RND.str(10);
    String line3 = RND.str(10);

    TestTunnel tunnel = new TestTunnel();
    tunnel.forGetRequestReader = line1 + '\n' + line2 + '\n' + line3;

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isInstanceOf(List.class);
    List actual = (List) actualParamValue;
    assertThat(actual).containsExactly(line1, line2, line3);
  }

  class ForRequestInput_byteArray {
    public void forTest(@RequestInput byte[] content) {
    }
  }

  @Test
  public void requestInput_byteArray() throws Exception {
    final Method method = getMethod(ForRequestInput_byteArray.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();
    tunnel.forGetRequestInputStream = RND.byteArray(100);

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isInstanceOf(byte[].class);
    assertThat(actualParamValue).isEqualTo(tunnel.forGetRequestInputStream);
  }

  class ForRequestInput_InputStream {
    public void forTest(@RequestInput InputStream requestContentInputStream) {
    }
  }

  @Test
  public void requestInput_InputStream() throws Exception {
    final Method method = getMethod(ForRequestInput_InputStream.class, "forTest");

    final MethodParameterValueExtractor e = MethodParameterMeta.create(method).get(0);

    final CatchResult catchResult = new CatchResult();

    TestTunnel tunnel = new TestTunnel();
    tunnel.forGetRequestInputStream = RND.byteArray(100);

    final Object actualParamValue = e.extract(catchResult, tunnel);

    assertThat(actualParamValue).isInstanceOf(InputStream.class);

    ByteArrayOutputStream actual = new ByteArrayOutputStream();
    {
      byte[] buffer = new byte[1024 * 4];
      InputStream in = (InputStream) actualParamValue;
      while (true) {
        final int count = in.read(buffer);
        if (count < 0) break;
        actual.write(buffer, 0, count);
      }
    }

    assertThat(actual.toByteArray()).isEqualTo(tunnel.forGetRequestInputStream);
  }
}
