package cn.xiaowenjie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.ResultBean;
import cn.xiaowenjie.services.IConfigService;

/**
 * 实现最简单的的config操作d
 * 
 * @author 晓风轻 https://github.com/xwjie/HessianDemo
 *
 */
public class ConfigServiceImpl implements IConfigService {

	private final ConcurrentSkipListMap<Long, Config> configs = new ConcurrentSkipListMap<Long, Config>();

	private static final AtomicLong idSequence = new AtomicLong(1000L);

	@Override
	public ResultBean<Long> addConfig(Config config) {
		System.out.println("ConfigServiceImpl.addConfig：" + config);

		long id = idSequence.incrementAndGet();

		config.setId(id);
		configs.put(id, config);

		return new ResultBean<>(id);
	}

	@Override
	public ResultBean<Collection<Config>> getAll() {
		System.out.println("ConfigServiceImpl.GetAll：=======打印一个日志，后面会用到========");
		// FIXME 这样返回kcyo反序列化会有错误！！
		// Exception in thread "main" com.esotericsoftware.kryo.KryoException: Class
		// cannot be created (missing no-arg constructor):
		// java.util.concurrent.ConcurrentSkipListMap$Values
		// return new ResultBean<>(configs.values());

		return new ResultBean<>(new ArrayList<>(configs.values()));
	}

	@Override
	public ResultBean<Boolean> delete(long id) {
		System.out.println("ConfigServiceImpl.Delete：" + id);
		return new ResultBean<>(configs.remove(id) != null);
	}

	public static void main(String[] args) {
		ConfigServiceImpl c = new ConfigServiceImpl();

		Config config = new Config();

		c.addConfig(config);

		System.out.println(c.configs.values().size());
		System.out.println(new ArrayList<>(c.configs.values()).size());

		System.out.println(c.getAll());
	}
}
