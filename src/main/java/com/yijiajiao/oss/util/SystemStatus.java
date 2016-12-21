package com.yijiajiao.oss.util;

public enum SystemStatus {

  OK(200, "success"),
  
  NO_DATE(200, "查询结果为空"),
  // 500- 服务器错误
  SERVER_ERROR(500, "服务器异常"),
  // 400- 请求错误
  BAD_REQUEST(400, "Bad Request"), 
  UNAUTHORIZED(401, "参数不匹配"), 
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
  
  ID_NOT_SYSTEM(404, "请求错误"),
  
  NOT_SYSTEM_STATE(404, "数据请求错误"),
  
  ID_NOT_FOUND(400001, "请求参数不匹配");
  

 
  SystemStatus(int status, String str) {
    setCode(status);
    setStr(str);
  }

  private int    code;

  private String str;

  public int getCode() {
    return code;
  }

  public String getStr() {
    return str;
  }

  private void setCode(int code) {
    this.code = code;
  }

  private void setStr(String str) {
    this.str = str;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("code");
    sb.append(":");
    sb.append(code);
    sb.append(",");
    sb.append("message");
    sb.append(":");
    sb.append(str);
    return sb.toString();
  }

}
