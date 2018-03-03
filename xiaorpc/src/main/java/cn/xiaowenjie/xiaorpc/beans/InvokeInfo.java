package cn.xiaowenjie.xiaorpc.beans;

import lombok.Data;

/**
 * 调用信息封装类
 * 
 * @author Administrator
 *
 */
@Data
public class InvokeInfo {

	private String method;

	private Object[] params;

}
