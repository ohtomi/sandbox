package com.example;

public class PojoItem {

  private int id;
  private String name;

  public PojoItem() {
  }

  public PojoItem(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "PojoItem [" + "id:" + id + ", name:" + name + "]";
  }

}
