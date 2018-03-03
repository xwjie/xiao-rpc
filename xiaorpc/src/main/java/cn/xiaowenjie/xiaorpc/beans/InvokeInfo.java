package cn.xiaowenjie.xiaorpc.beans;

import java.util.Arrays;

import lombok.Data;

@Data
public class InvokeInfo {

	private String method;

	private Object[] params;

}
