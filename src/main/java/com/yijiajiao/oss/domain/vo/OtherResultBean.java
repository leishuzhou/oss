package com.yijiajiao.oss.domain.vo;


public class OtherResultBean {

	
	  public String requestId;
	  public String code;
	  public String httpCode;
	  public String message;
	  public Object result = "";

	  public String getMessage() {
	    return message;
	  }
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
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
	public String getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}


}
