package me.machadolucas.dataconverter;

import java.util.InputMismatchException;

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
		return null;
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
		return null;
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

}
