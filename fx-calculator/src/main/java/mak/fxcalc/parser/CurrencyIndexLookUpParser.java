package mak.fxcalc.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mak.fxcalc.util.ParsedObject;

public class CurrencyIndexLookUpParser implements IParser<Map<String, Integer>>{

	private static final int FIRST_LINE = 0;
	private Pattern csvStrippingPattern;

	public CurrencyIndexLookUpParser(Pattern csvStrippingPattern) {
		this.csvStrippingPattern = csvStrippingPattern;
	}

	@Override
	public ParsedObject<Map<String, Integer>> parseValidatedLines(List<String> validatedInputLines) {
		if (null == validatedInputLines || validatedInputLines.isEmpty()) {
			final ParsedObject<Map<String, Integer>> emptyParsedObject = new ParsedObject<>(Collections.emptyMap());
			return emptyParsedObject;
		}
		final Map<String,Integer> currencyIndexMap= new HashMap<>();
		String firstLineListOfCurrencies = validatedInputLines.get(FIRST_LINE);
		List<String> currenciesToIndex  = Arrays.asList(csvStrippingPattern.split(firstLineListOfCurrencies));
		indexAllCurrencies(currencyIndexMap, currenciesToIndex);
		final ParsedObject<Map<String, Integer>> parsedObject = new ParsedObject<>(currencyIndexMap);
		return parsedObject;
	}

	private void indexAllCurrencies(final Map<String, Integer> currencyIndexMap, final List<String> currenciesToIndex) {
		int index=0;
		for (String currency : currenciesToIndex) {
			currencyIndexMap.put(currency, index++);
		}
	}
}