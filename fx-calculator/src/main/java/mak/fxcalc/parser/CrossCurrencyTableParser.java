package mak.fxcalc.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import mak.fxcalc.util.ParsedObject;

public class CrossCurrencyTableParser implements IParser<String[][]>{
	final private Pattern csvStrippingPattern;
	public CrossCurrencyTableParser(Pattern csvStrippingPattern) {
		this.csvStrippingPattern = csvStrippingPattern;
	}

	@Override
	public ParsedObject<String[][]> parseValidatedLines(final List<String> validatedInputLines) {
		if (null == validatedInputLines || validatedInputLines.isEmpty()) return null;
		final String[][] crossCurrencyMatrix = getPopulatedCrossCurrencyMatrix(validatedInputLines);
		final ParsedObject<String[][]> parsedObject = new ParsedObject<>(crossCurrencyMatrix);
		return parsedObject;
	}
	
	private String[][] getPopulatedCrossCurrencyMatrix(final List<String> validatedInputLines){
		return validatedInputLines.stream()
								  .map(line -> Arrays.asList(this.csvStrippingPattern.split(line)))
								  .map(entries -> entries.toArray())
								  .toArray(String[][]::new);
	}
}