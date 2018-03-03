package cn.xiaowenjie;

import javax.servlet.annotation.WebServlet;

import cn.xiaowenjie.xiaorpc.server.XiaoRPCServlet;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/xiaorpc")
public class ApiServlet extends XiaoRPCServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getServerTarget() {
		return new ConfigServiceImpl();
	}

}
