package com.example;

public class UserException extends RemoteException {

	private static final long serialVersionUID = -5586637225866475833L;

	private String name;
	private String data;
	private PojoItem item;

	public UserException() {
	}

	public UserException(String name, String data, PojoItem item) {
		this.name = name;
		this.data = data;
		this.item = item;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setItem(PojoItem item) {
		this.item = item;
	}

	public PojoItem getItem() {
		return item;
	}

	@Override
	public String toString() {
		return "UserException [" + "name:" + name + ", data:" + data
				+ ", item:" + item + "]";
	}

}
