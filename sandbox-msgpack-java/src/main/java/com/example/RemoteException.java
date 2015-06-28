package com.example;

public class RemoteException extends RuntimeException {

  private String name;

  public RemoteException() {
  }

  public RemoteException(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "RemoteException [" + "name:" + name + "]";
  }

}
