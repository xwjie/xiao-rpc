package cn.xiaowenjie.xiaorpc.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISerialize {

	Object read(InputStream is, Class<?> cls);

	void write(OutputStream os, Object obj);
}
