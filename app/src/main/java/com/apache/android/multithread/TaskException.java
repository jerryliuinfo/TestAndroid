package com.apache.android.multithread;

/**
 * 应用的异常申明<br/>
 * 1、包含四种基本环境类型错误申明<br/>
 * 2、业务类型异常，如果没有设置msg字段，请初始化Declare获取msg信息
 *
 */
public class TaskException extends Exception {



	private static final long serialVersionUID = -6262214243381380676L;

	public enum TaskError {
		// 网络错误
		failIOError,
		// 无网络链接
		noneNetwork, 
		// 连接超时
		timeout, 
		// 响应超时
		socketTimeout,
		// 返回数据不合法
		resultIllegal
	}
	
	private String code;//这里定义成int的原因是有时候 需要一个msg的构造方法，如果code和msg都定义成String会有冲突

    private String message = "";
	


	public TaskException(){

	}

	public TaskException(String message) {
		this.message = message;
	}

	public TaskException(String code, String msg) {
		this.code = code;
		this.message = msg;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
        return code;
    }


	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {


		return super.getMessage() + "";
	}





}
