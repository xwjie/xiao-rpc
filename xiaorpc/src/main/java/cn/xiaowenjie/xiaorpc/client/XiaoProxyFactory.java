package cn.xiaowenjie.xiaorpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
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

	/**
	 * JNDI object factory so the proxy can be used as a resource.
	 */
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		Reference ref = (Reference) obj;

	    String api = null;
	    String url = null;
	    
	    for (int i = 0; i < ref.size(); i++) {
	      RefAddr addr = ref.get(i);

	      String type = addr.getType();
	      String value = (String) addr.getContent();

	      if (type.equals("type"))
	        api = value;
	      else if (type.equals("url"))
	        url = value;
	      //else if (type.equals("user"))
	        //setUser(value);
	      //else if (type.equals("password"))
	        //setPassword(value);
	    }

	    if (url == null)
	      throw new NamingException("`url' must be configured for HessianProxyFactory.");
	    // XXX: could use meta protocol to grab this
	    if (api == null)
	      throw new NamingException("`type' must be configured for HessianProxyFactory.");

	    Class apiClass = Class.forName(api, false, _loader);

	    return create(apiClass, url);
	}

}
