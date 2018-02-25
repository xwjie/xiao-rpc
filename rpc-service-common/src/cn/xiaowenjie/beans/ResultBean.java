package cn.xiaowenjie.beans;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg = "success";

	private T data;

	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();

		this.data = data;
	}
}