package me.machadolucas.dataconverter;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.machadolucas.dataconverter.template.ArrayOfJSON;
import me.machadolucas.dataconverter.template.JSONObject;
import me.machadolucas.dataconverter.template.JSONPair;
import me.machadolucas.dataconverter.util.DataType;
import me.machadolucas.dataconverter.util.DataUtils;

/**
 * <p>
 * Main class of the dataconverter library.
 * </p>
 * <p>
 * You can use its methods to convert data between CSV, TSV and JSON formats.
 * </p>
 * 
 * @author <a href="http://machadolucas.me">machadolucas</a>
 * 
 */
public class Converter {

	/**
	 * Converts a CSV String to a JSON String
	 * 
	 * @param csv
	 *            a String with data separated with commas (,) and lines (\n).
	 *            For example: "COLUMN1,COLUMN2\ndataX1,dataX2\ndataY1,dataY2"
	 * @return the data in a array of JSON objects String. For example:
	 *         "[{\"COLUMN1\":dataX1,\"
	 *         COLUMN2\":dataX2},{\"COLUMN1\":dataY1,\"COLUMN2\":dataY2}]"
	 */
	public String csvToJson(String csv) {
		if (DataUtils.checkDataType(csv) != DataType.CSV) {
			throw new InputMismatchException(
					"Input String is not in CSV format");
		}
		ArrayOfJSON array = buildJSONFromString(csv, ",");
		return array.toString();
	}

	/**
	 * Converts a TSV String to a JSON String
	 * 
	 * @param tsv
	 *            a String with data separated with tabs (\t) and lines (\n).
	 *            For example:
	 *            "COLUMN1\tCOLUMN2\ndataX1\tdataX2\ndataY1\tdataY2"
	 * @return the data in a array of JSON objects String. For example:
	 *         "[{\"COLUMN1\":dataX1,\"
	 *         COLUMN2\":dataX2},{\"COLUMN1\":dataY1,\"COLUMN2\":dataY2}]"
	 */
	public String tsvToJson(String tsv) {
		if (DataUtils.checkDataType(tsv) != DataType.TSV) {
			throw new InputMismatchException(
					"Input String is not in TSV format");
		}
		ArrayOfJSON array = buildJSONFromString(tsv, "\t");
		return array.toString();
	}

	/**
	 * Converts a JSON String to a CSV String
	 * 
	 * @param json
	 *            the data in an array of JSON objects as a String. For example:
	 *            "[{\"COLUMN1\":dataX1,\"
	 *            COLUMN2\":dataX2},{\"COLUMN1\":dataY1,\"COLUMN2\":dataY2}]"
	 * 
	 * @return a String with data separated with commas (,) and lines (\n). For
	 *         example: "COLUMN1,COLUMN2\ndataX1,dataX2\ndataY1,dataY2"
	 */
	public String jsonToCsv(String json) {
		if (DataUtils.checkDataType(json) != DataType.JSON) {
			throw new InputMismatchException(
					"Input String is not in JSON format");
		}
		return buildXSPFromJSON(json, ",");
	}

	/**
	 * Converts a TSV String to a JSON String
	 * 
	 * @param json
	 *            the data in an array of JSON objects as a String. For example:
	 *            "[{\"COLUMN1\":dataX1,\"
	 *            COLUMN2\":dataX2},{\"COLUMN1\":dataY1,\"COLUMN2\":dataY2}]"
	 * 
	 * @return a String with data separated with tabs (\t) and lines (\n). For
	 *         example: "COLUMN1\tCOLUMN2\ndataX1\tdataX2\ndataY1\tdataY2"
	 */
	public String jsonToTsv(String json) {
		if (DataUtils.checkDataType(json) != DataType.JSON) {
			throw new InputMismatchException(
					"Input String is not in JSON format");
		}
		return buildXSPFromJSON(json, "\t");
	}

	/**
	 * Converts a CSV String to a TSV String
	 * 
	 * @param csv
	 *            a String with data separated with commas (,) and lines (\n).
	 *            For example: "COLUMN1,COLUMN2\ndataX1,dataX2\ndataY1,dataY2"
	 * @return the data as a String separated with tabs (\t) and lines (\n). For
	 *         example: "COLUMN1\tCOLUMN2\ndataX1\tdataX2\ndataY1\tdataY2"
	 */
	public String csvToTsv(String csv) {
		if (DataUtils.checkDataType(csv) != DataType.CSV) {
			throw new InputMismatchException(
					"Input String is not in CSV format");
		}
		return csv.replaceAll("\t", " ").replaceAll(",", "\t");
	}

	/**
	 * Converts a TSV String to a CSV String
	 * 
	 * @param tsv
	 *            a String with data separated with tabs (\t) and lines (\n).
	 *            For example:
	 *            "COLUMN1\tCOLUMN2\ndataX1\tdataX2\ndataY1\tdataY2"
	 * @return the data as a String separated with commas (,) and lines (\n).
	 *         For example: "COLUMN1,COLUMN2\ndataX1,dataX2\ndataY1,dataY2"
	 */
	public String tsvToCsv(String tsv) {
		if (DataUtils.checkDataType(tsv) != DataType.TSV) {
			throw new InputMismatchException(
					"Input String is not in TSV format");
		}
		return tsv.replaceAll(",", " ").replaceAll("\t", ",");
	}

	private ArrayOfJSON buildJSONFromString(String text, String separator) {
		String[] lines = text.split("\n");

		String columns[] = lines[0].split(separator);

		ArrayOfJSON array = new ArrayOfJSON();
		for (int i = 1; i < lines.length; i++) {
			JSONObject json = new JSONObject();
			String values[] = lines[i].split(separator);

			for (int j = 0; j < values.length; j++) {
				JSONPair pair = new JSONPair(columns[j], values[j]);
				json.addPair(pair);
			}
			array.addObject(json);
		}
		return array;
	}

	private String buildXSPFromJSON(String json, String separator) {

		Pattern pattern = Pattern.compile("\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(json);

		StringBuilder output = new StringBuilder();

		Set<String> properties = new TreeSet<String>();
		while (matcher.find()) {
			String[] pairs = matcher.group(1).split(",");
			for (String pair : pairs) {
				properties.add(pair.split(":")[0].replaceAll("\"", ""));
			}
		}

		Iterator<String> it = properties.iterator();
		while (it.hasNext()) {
			output.append(it.next()).append(separator);
		}
		output.setLength(output.length() - 1);
		output.append("\n");

		matcher = pattern.matcher(json);
		while (matcher.find()) {
			String[] pairs = matcher.group(1).split(",");
			Map<String, String> map = new HashMap<String, String>();
			for (String pair : pairs) {
				String[] pairArray = pair.split(":");
				map.put(pairArray[0].replace("\"", ""), pairArray[1]);
			}
			it = properties.iterator();
			while (it.hasNext()) {
				output.append(map.get(it.next())).append(separator);
			}
			output.setLength(output.length() - 1);
			output.append("\n");
		}
		output.setLength(output.length() - 1);

		return output.toString();
	}
}
