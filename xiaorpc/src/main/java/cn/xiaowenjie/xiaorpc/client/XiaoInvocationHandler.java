package cn.xiaowenjie.xiaorpc.client;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public class XiaoInvocationHandler implements InvocationHandler {

	private final HttpClient httpClient;
	private final URI uri;

	public XiaoInvocationHandler(URI uri, HttpClient httpClient) {
		this.httpClient = httpClient;
		this.uri = uri;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("XiaoInvocationHandler.invoke(): " + method);

		// 根据调用方法生成http请求
		HttpUriRequest request = generateRequest(method, args);

		// 执行的得到返回值
		HttpResponse response = httpClient.execute(request);

		// 把返回的数据反序列化
		InputStream in = response.getEntity().getContent();

		return null;
	}

	private HttpUriRequest generateRequest(Method method, Object[] args) {

		HttpPost httpPost = new HttpPost(this.uri);

		httpPost.addHeader("x-method", method.getName());

		return httpPost;
	}

}
