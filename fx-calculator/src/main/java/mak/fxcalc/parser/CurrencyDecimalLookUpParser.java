package mak.fxcalc.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mak.fxcalc.util.ParsedObject;

public class CurrencyDecimalLookUpParser implements IParser<Map<String, Integer>>{
	private final Pattern currencyDecimalPlacesPattern;
	public CurrencyDecimalLookUpParser(Pattern currencyDecimalPlacesPattern) {
		this.currencyDecimalPlacesPattern = currencyDecimalPlacesPattern;
	}

	@Override
	public ParsedObject<Map<String, Integer>> parseValidatedLines(List<String> validatedInputLines) {
		final ParsedObject<Map<String, Integer>> emptyParsedObject = new ParsedObject<>(Collections.emptyMap());
		if (validatedInputLines==null || validatedInputLines.isEmpty()) return emptyParsedObject;
		Map<String,Integer> currencyDecimalLookUpMap= new HashMap<>();
		for (String line : validatedInputLines) {
		    Matcher matcher = currencyDecimalPlacesPattern.matcher(line);
		    if (matcher.find())
		    {
		        final String currencyChar1=matcher.group(1);
		        final String currencyChar2=matcher.group(2);
		        final String currencyChar3=matcher.group(3);
		        final String decimalPlaces=matcher.group(5);
		        final String currency = currencyChar1+currencyChar2+currencyChar3;
		        currencyDecimalLookUpMap.put(currency,Integer.parseInt(decimalPlaces));
		    }else{
		    	return emptyParsedObject;
		    }
		  }
		final ParsedObject<Map<String, Integer>> parsedObject = new ParsedObject<>(currencyDecimalLookUpMap);
		return parsedObject;
	}
}