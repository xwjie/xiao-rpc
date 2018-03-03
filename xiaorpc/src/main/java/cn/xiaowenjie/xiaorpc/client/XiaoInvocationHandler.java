package cn.xiaowenjie.xiaorpc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;

import cn.xiaowenjie.xiaorpc.beans.InvokeInfo;
import cn.xiaowenjie.xiaorpc.serialize.ISerialize;
import cn.xiaowenjie.xiaorpc.serialize.KcyoSerialize;

public class XiaoInvocationHandler implements InvocationHandler {

	private final HttpClient httpClient;
	private final URI uri;
	private ISerialize serializeTool;

	public XiaoInvocationHandler(URI uri, HttpClient httpClient) {
		this.httpClient = httpClient;
		this.uri = uri;
		this.serializeTool = KcyoSerialize.getInstance();
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("XiaoInvocationHandler.invoke(): " + method);

		// 根据调用方法生成http请求
		HttpUriRequest request = generateRequest(method, args);
		// HttpResponse response = httpClient.execute(request);

		// 执行的得到返回值
		Object result = httpClient.execute(request, new ResponseHandler<Object>() {

			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				return serializeTool.read(response.getEntity().getContent());
			}
		});

		System.out.println("服务器返回结果:" + result);

		return result;
	}

	private HttpUriRequest generateRequest(Method method, Object[] args) {
		final InvokeInfo invokeInfo = toInvokeInfo(method, args);

		HttpPost httpPost = new HttpPost(this.uri);

		// 请求里面把字节码写过去
		httpPost.setEntity(new BasicHttpEntity() {
			@Override
			public void writeTo(OutputStream outstream) throws IOException {
				serializeTool.write(outstream, invokeInfo);
			}
		});

		return httpPost;
	}

	private InvokeInfo toInvokeInfo(Method method, Object[] args) {
		InvokeInfo info = new InvokeInfo();

		info.setMethod(method.getName());
		info.setParams(args);

		return info;
	}

}
