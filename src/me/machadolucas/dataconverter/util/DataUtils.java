package me.machadolucas.dataconverter.util;

public class DataUtils {

	public static DataType checkDataType(String input) {

		try {
			String firstLine = input.substring(0, input.indexOf("\n"));
			if (firstLine.contains(",")) {
				return DataType.CSV;
			}
			if (firstLine.contains("\t")) {
				return DataType.TSV;
			}
			if (firstLine.contains("\t")) {
				return DataType.TSV;
			}

		} catch (IndexOutOfBoundsException e) {
			if (input.contains("{") && input.contains("}")) {
				return DataType.JSON;
			}
		}
		return null;

	}
}
