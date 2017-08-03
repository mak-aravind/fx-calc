package mak.fxcalc.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mak.fxcalc.util.ParsedObject;

public class ConversionRateFeedTableParser implements IParser<Map<String, Float>>{
	private final Pattern currencyConversionRatePattern;
	public ConversionRateFeedTableParser(Pattern currencyConversionRatePattern) {
		this.currencyConversionRatePattern = currencyConversionRatePattern;
	}

	@Override
	public ParsedObject<Map<String, Float>> parseValidatedLines(List<String> validatedInputLines) {
		final ParsedObject<Map<String, Float>> emptyParsedObject = new ParsedObject<>(Collections.emptyMap());
		if (validatedInputLines==null || validatedInputLines.isEmpty()) return emptyParsedObject;
		final Map<String, Float> conversionRateFeedLookUpMap = new HashMap<>();
		for (String line : validatedInputLines) {
		    final Matcher matcher = currencyConversionRatePattern.matcher(line);
		    if (matcher.find())
		    {
		        final String baseCurrency = getParsedBaseCurrency(matcher);
		        final String termsCurrency = getParsedTermCurrency(matcher);
		        final String baseTermCurrenciesComboKey = baseCurrency + termsCurrency;
		        final String currenciesInvertedComboKey = termsCurrency + baseCurrency;
		        final Float conversionRate=Float.parseFloat(matcher.group(8));
		        final Float invertedConverionRate = Math.round ((1F/conversionRate) * 10000.0f) / 10000.0f;
		        conversionRateFeedLookUpMap.put(baseTermCurrenciesComboKey, conversionRate);
		        conversionRateFeedLookUpMap.put(currenciesInvertedComboKey, invertedConverionRate);
		    }else{
		    	return emptyParsedObject;
		    }
		  }
		final ParsedObject<Map<String, Float>> parsedObject = new ParsedObject<>(conversionRateFeedLookUpMap);
        return parsedObject;
	}
	
	private String getParsedBaseCurrency(final Matcher matcher){
		final String baseCurrencyChar1=matcher.group(1);
        final String baseCurrencyChar2=matcher.group(2);
        final String baseCurrencyChar3=matcher.group(3);
        return baseCurrencyChar1 + baseCurrencyChar2 + baseCurrencyChar3;
	}
	
	private String getParsedTermCurrency(final Matcher matcher){
        final String termsCurrencyChar1=matcher.group(4);
        final String termsCurrencyChar2=matcher.group(5);
        final String termsCurrencyChar3=matcher.group(6);
        return termsCurrencyChar1 + termsCurrencyChar2 + termsCurrencyChar3;
	}
}