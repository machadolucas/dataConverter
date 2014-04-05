package me.machadolucas.dataconverter.template;

import java.util.ArrayList;
import java.util.List;

public class JSONObject {

	private final List<JSONPair> pairs = new ArrayList<JSONPair>();

	public void addPair(JSONPair pair) {
		this.pairs.add(pair);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("{");
		for (JSONPair pair : pairs) {
			str.append(pair.toString()).append(",");
		}
		str.setLength(str.length() - 1);
		str.append("}");
		return str.toString();
	}
}
