package me.machadolucas.dataconverter.template;

import java.util.ArrayList;
import java.util.List;

public class ArrayOfJSON {

	private List<JSONObject> objects = new ArrayList<JSONObject>();

	public List<JSONObject> getObjects() {
		return objects;
	}

	public void setObjects(List<JSONObject> objects) {
		this.objects = objects;
	}

	public void addObject(JSONObject object) {
		this.objects.add(object);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		for (JSONObject object : objects) {
			str.append(object.toString()).append(",");
		}
		String result = str.substring(0, str.length() - 1) + "]";
		return result;
	}

}
