package cn.xiaowenjie.xiaorpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class XiaoProxyFactory implements ObjectFactory {

	public Object create(String url, Class<?> service) throws URISyntaxException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		HttpClient httpClient = HttpClients.createDefault();
		InvocationHandler handler = new XiaoInvocationHandler(new URI(url), httpClient);

		return Proxy.newProxyInstance(loader, new Class<?>[] { service }, handler);
	}

	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		return null;
	}

}
