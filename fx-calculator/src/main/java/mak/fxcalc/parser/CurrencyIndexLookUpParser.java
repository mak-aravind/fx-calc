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
	public ParsedObject<Map<String, Integer>> parseValidatedLines(final List<String> validatedInputLines) {
		if (null == validatedInputLines || validatedInputLines.isEmpty()) {
			return new ParsedObject<>(Collections.emptyMap());
		}
		final Map<String,Integer> currencyIndexMap= new HashMap<>();
		String firstLineListOfCurrencies = validatedInputLines.get(FIRST_LINE);
		List<String> currenciesToIndex  = Arrays.asList(this.csvStrippingPattern.split(firstLineListOfCurrencies));
		indexAllCurrencies(currencyIndexMap, currenciesToIndex);
		final ParsedObject<Map<String, Integer>> parsedObject = new ParsedObject<>(Collections.unmodifiableMap(currencyIndexMap));
		return parsedObject;
	}

	private void indexAllCurrencies(final Map<String, Integer> currencyIndexMap, final List<String> currenciesToIndex) {
		int index=0;
		for (String currency : currenciesToIndex) {
			currencyIndexMap.put(currency, index++);
		}
	}
}