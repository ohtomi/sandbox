package com.example;

public class Request {

	private int type;
	private int id;
	private String method;
	private Object[] args;

	public Request() {
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

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "Request [" + "type:" + type + ", id:" + id + ", method:"
				+ method + ", args:" + join(args) + "]";
	}

	private String join(Object[] args) {
		String result = "[";
		for (int i = 0; i < args.length; i++) {
			result += (args[i] + ", ");
		}
		result += "]";
		return result;
	}

}
