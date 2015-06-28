package com.example;

public class Response {

  private int type;
  private int id;
  private Object error;
  private Object result;

  public Response() {
  }

  public void setType(int type) {
    this.type = type;
  }
  public int getType() {
    return type;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }
  public void setError(Object error) {
    this.error = error;
  }
  public Object getError() {
    return error;
  }
  public void setResult(Object result) {
    this.result = result;
  }
  public Object getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "Response ["
        + "type:" + type
        + ", id:" + id
        + ", error:" + error
        + ", result:" + result
        + "]";
  }

}
