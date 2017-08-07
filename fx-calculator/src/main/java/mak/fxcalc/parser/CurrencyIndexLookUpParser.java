package mak.fxcalc.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mak.fxcalc.util.ParsedObject;

public class CurrencyIndexLookUpParser implements IParser<Map<String, Integer>>{

	private static final int FIRST_LINE = 0;
	private Pattern csvStrippingPattern;

	public CurrencyIndexLookUpParser(final Pattern csvStrippingPattern) {
		this.csvStrippingPattern = csvStrippingPattern;
	}

	@Override
	public ParsedObject<Map<String, Integer>> parseValidatedLines(final List<String> validatedInputLines) {
		if (null == validatedInputLines || validatedInputLines.isEmpty()) return new ParsedObject<>(Collections.emptyMap());
		final String firstLineListOfCurrencies = validatedInputLines.get(FIRST_LINE);
		final List<String> currenciesToIndex  = Arrays.asList(this.csvStrippingPattern.split(firstLineListOfCurrencies));
		final Map<String,Integer> currencyIndexMap= getCurrenciesIndexedMap(currenciesToIndex);
		final ParsedObject<Map<String, Integer>> parsedObject = new ParsedObject<>(Collections.unmodifiableMap(currencyIndexMap));
		return parsedObject;
	}
	
	private Map<String,Integer> getCurrenciesIndexedMap(final List<String> currenciesToIndex){
		final AtomicInteger index = new AtomicInteger(-1);
		return currenciesToIndex.stream()
								.collect(Collectors.toMap(String::toString, currency -> index.incrementAndGet()));
	}
}