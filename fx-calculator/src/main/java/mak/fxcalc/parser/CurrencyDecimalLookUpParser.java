package mak.fxcalc.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mak.fxcalc.util.ParsedObject;

public class CurrencyDecimalLookUpParser implements IParser<Map<String, Integer>>{
	private final Pattern currencyDecimalPlacesPattern;
	public CurrencyDecimalLookUpParser(Pattern currencyDecimalPlacesPattern) {
		this.currencyDecimalPlacesPattern = currencyDecimalPlacesPattern;
	}

	@Override
	public ParsedObject<Map<String, Integer>> parseValidatedLines(final List<String> validatedInputLines) {
		final ParsedObject<Map<String, Integer>> emptyParsedObject = new ParsedObject<>(Collections.emptyMap());
		if (validatedInputLines==null || validatedInputLines.isEmpty()) return emptyParsedObject;
		Map<String,Integer> currencyDecimalLookUpMap= getCurrencyDecimalLookUpMap(validatedInputLines);
		final ParsedObject<Map<String, Integer>> parsedObject = new ParsedObject<>(Collections.unmodifiableMap(currencyDecimalLookUpMap));
		return parsedObject;
	}
	
	private Map<String,Integer> getCurrencyDecimalLookUpMap(final List<String> validatedInputLines){
		return validatedInputLines.stream()
						   		  .map(line -> this.currencyDecimalPlacesPattern.matcher(line))
						   		  .filter(matcher -> matcher.find())
						   		  .collect(Collectors.toMap(matcher -> getParsedTermCurrency(matcher), 
						   				  					matcher -> Integer.parseInt(matcher.group(5))));
	}
	
	private String getParsedTermCurrency(final Matcher matcher){
        final String currencyChar1=matcher.group(1);
        final String currencyChar2=matcher.group(2);
        final String currencyChar3=matcher.group(3);
        return currencyChar1+currencyChar2+currencyChar3;
	}
}