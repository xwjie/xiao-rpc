package cn.xiaowenjie.beans;

import java.io.Serializable;

public class Config implements Serializable {

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private static final long serialVersionUID = 1L;

	private long id;

	private String name, value;

	@Override
	public String toString() {
		return "Config [id=" + id + ", name=" + name + ", value=" + value + "]";
	}

}
