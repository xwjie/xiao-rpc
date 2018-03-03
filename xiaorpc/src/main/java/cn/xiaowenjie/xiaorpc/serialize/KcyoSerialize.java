package cn.xiaowenjie.xiaorpc.serialize;

import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KcyoSerialize implements ISerialize {

	public final static KcyoSerialize instance = new KcyoSerialize();
	private final Kryo kryo;

	private KcyoSerialize() {
		this.kryo = new Kryo();
	}

	public static ISerialize getInstance() {
		return instance;
	}

	public Object read(InputStream is) {
		Input input = new Input(is);
		try {
			Object obj = kryo.readClassAndObject(input);
			return obj;
		} finally {
			input.close();
		}
	}

	public void write(OutputStream os, Object obj) {
		Output output = new Output(os);
		try {
			kryo.writeClassAndObject(output, obj);
		} finally {
			output.close();
		}
	}

}
