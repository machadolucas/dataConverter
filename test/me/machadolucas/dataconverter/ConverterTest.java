package me.machadolucas.dataconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConverterTest {

	public final static String CSV_SAMPLE = "gender,age\nmale,14\nmale,17\nfemale,14\nfemale,21\nmale,22\nmale,23";
	public final static String CSV_SAMPLE2 = "age,gender\n14,male\n17,male\n14,female\n21,female\n22,male\n23,male";
	public final static String TSV_SAMPLE = "gender\tage\nmale\t14\nmale\t17\nfemale\t14\nfemale\t21\nmale\t22\nmale\t23";
	public final static String TSV_SAMPLE2 = "age\tgender\n14\tmale\n17\tmale\n14\tfemale\n21\tfemale\n22\tmale\n23\tmale";
	public final static String JSON_SAMPLE = "[{\"gender\":male,\"age\":14},{\"gender\":male,\"age\":17},{\"gender\":female,\"age\":14},{\"gender\":female,\"age\":21},{\"gender\":male,\"age\":22},{\"gender\":male,\"age\":23}]";

	Converter converter = new Converter();

	@Test
	public void testCsvToJson() {

		assertEquals(JSON_SAMPLE, converter.csvToJson(CSV_SAMPLE));
	}

	@Test
	public void testTsvToJson() {
		assertEquals(JSON_SAMPLE, converter.tsvToJson(TSV_SAMPLE));
	}

	@Test
	public void testJsonToCsv() {
		assertTrue("Unexpected value",
				converter.jsonToCsv(JSON_SAMPLE).equals(CSV_SAMPLE)
						|| converter.jsonToCsv(JSON_SAMPLE).equals(CSV_SAMPLE2));
	}

	@Test
	public void testJsonToTsv() {
		assertTrue("Unexpected value",
				converter.jsonToTsv(JSON_SAMPLE).equals(TSV_SAMPLE)
						|| converter.jsonToTsv(JSON_SAMPLE).equals(TSV_SAMPLE2));
	}

	@Test
	public void testCsvToTsv() {
		assertEquals(TSV_SAMPLE, converter.csvToTsv(CSV_SAMPLE));
	}

	@Test
	public void testTsvToCsv() {

		assertEquals(CSV_SAMPLE, converter.tsvToCsv(TSV_SAMPLE));
	}

}
