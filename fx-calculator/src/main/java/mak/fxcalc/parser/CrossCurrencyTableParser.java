package mak.fxcalc.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import mak.fxcalc.util.ParsedObject;

public class CrossCurrencyTableParser implements IParser<String[][]>{
	final private Pattern csvStrippingPattern;
	public CrossCurrencyTableParser(Pattern csvStrippingPattern) {
		this.csvStrippingPattern = csvStrippingPattern;
	}

	@Override
	public ParsedObject<String[][]> parseValidatedLines(final List<String> validatedInputLines) {
		if (validatedInputLines==null || validatedInputLines.isEmpty()) return null;
		final int size = validatedInputLines.size();
		final String[][] crossCurrencyMatrix = new String[size][size];
		int i=0;
		for (String line : validatedInputLines) {
			int j=0;
			final List<String> entriesInLine = Arrays.asList(this.csvStrippingPattern.split(line));
			for (String entry : entriesInLine) {
				crossCurrencyMatrix[i][j++] = entry;
			}
			++i;
		}
		final ParsedObject<String[][]> parsedObject = new ParsedObject<>(crossCurrencyMatrix);
		return parsedObject;
	}
}