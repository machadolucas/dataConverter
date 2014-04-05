package me.machadolucas.dataconverter.template;

public class JSONPair {

	public JSONPair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "\"" + name + "\":" + value;
	}
}
