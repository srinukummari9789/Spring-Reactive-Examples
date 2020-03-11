package com.example.ReactiveSpring.fluxandmonoPlayground;

public class CustomException extends Throwable {
	private String msg;
	
	public CustomException(Throwable e) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
