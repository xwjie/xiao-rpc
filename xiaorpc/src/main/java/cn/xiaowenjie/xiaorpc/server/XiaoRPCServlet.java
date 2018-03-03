package cn.xiaowenjie.xiaorpc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.xiaowenjie.xiaorpc.beans.InvokeInfo;
import cn.xiaowenjie.xiaorpc.serialize.ISerialize;
import cn.xiaowenjie.xiaorpc.serialize.KcyoSerialize;
import lombok.SneakyThrows;

/**
 * Servlet implementation class ApiServlet
 */
public class XiaoRPCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Class<?> serverClass;
	private Object serverTaget;
	private ISerialize serializeTool;
	private final Map<String, Method> methodMap;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public XiaoRPCServlet() {
		super();

		this.serializeTool = KcyoSerialize.getInstance();
		this.methodMap = new HashMap<String, Method>();
		
		initTarget();
	}

	/**
	 * 得到提供服务的类
	 */
	private void initTarget() {
		// 得到要服务的类名和实例
		this.serverTaget = doGetServerTarget();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * FIXME 有了service方法就不会进入doGet等方法
	 * 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected final void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ApiServlet.service() ...");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!req.getMethod().equals("POST")) {
			res.setStatus(500); // , "Hessian Requires POST");
			PrintWriter out = res.getWriter();

			res.setContentType("text/html");
			out.println("<h1>Xiao RPC Requires POST</h1>");

			return;
		}

		// 调用方法并得到返回值
		Object result = invoke(req);

		this.serializeTool.write(response.getOutputStream(), result);
		response.flushBuffer();
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		System.out.println();
	}

	@SneakyThrows
	private Object invoke(HttpServletRequest req) {
		// 得到要调用的方法和参数
		InvokeInfo invokeInfo = (InvokeInfo) this.serializeTool.read(req.getInputStream());

		// 得到对于的方法
		Method method = getMethod(invokeInfo);

		Object result = method.invoke(this.serverTaget, invokeInfo.getParams());

		System.out.println("服务反射调用方法，执行结果：" + result);

		return result;
	}

	private Object doGetServerTarget() {
		Object obj = getServerTarget();

		// TODO 从配置里面获取
		if (obj == null) {

		}

		return obj;
	}

	protected Object getServerTarget() {
		return null;
	}

	/**
	 * 根据方法名和参数列表，找到对应的方法
	 * 
	 * @param invokeInfo
	 * @return
	 */
	@SneakyThrows
	private Method getMethod(InvokeInfo invokeInfo) {
		Object[] params = invokeInfo.getParams();

		Class<?>[] parameterTypes = null;

		if (params != null) {
			parameterTypes = new Class<?>[params.length];

			for (int i = 0; i < params.length; i++) {
				parameterTypes[i] = params[i].getClass();
			}
		}

		Method method = this.serverTaget.getClass().getMethod(invokeInfo.getMethod(), parameterTypes);
		return method;
	}

}
