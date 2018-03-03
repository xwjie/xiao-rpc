package cn.xiaowenjie;

import java.net.URISyntaxException;
import java.util.Collection;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.ResultBean;
import cn.xiaowenjie.services.IConfigService;
import cn.xiaowenjie.xiaorpc.client.XiaoProxyFactory;

public class XiaoRPCDemo {

	public static void main(String[] args) throws URISyntaxException {
		XiaoProxyFactory factory = new XiaoProxyFactory();

		String url = "http://localhost:8080/rpc-server-nospring/xiaorpc";

		IConfigService configService = (IConfigService) factory.create(url, IConfigService.class);

		Config config = new Config();

		config.setName("新的配置项名称");
		config.setValue("新的配置项的值");

		ResultBean<Long> addResult = configService.addConfig(config);
		System.out.println("addConfig:" + addResult);

		ResultBean<Collection<Config>> all = configService.getAll();
		System.out.println("getAll:" + all);

		// 把刚刚新建的删除掉
		ResultBean<Boolean> deleteResult = configService.delete(addResult.getData());
		System.out.println("deleteResult:" + deleteResult);
	}

}
