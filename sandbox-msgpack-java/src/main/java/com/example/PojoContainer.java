package com.example;

public class PojoContainer {

  private int id;
  private String name;
  private PojoItem item;

  public PojoContainer() {
  }

  public PojoContainer(int id, String name, PojoItem item) {
    this.id = id;
    this.name = name;
    this.item = item;
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
  public void setItem(PojoItem item) {
    this.item = item;
  }
  public PojoItem getItem() {
    return item;
  }

  @Override
  public String toString() {
    return "PojoContainer [" + "id:" + id + ", name:" + name + ", item:" + item + "]";
  }

}
