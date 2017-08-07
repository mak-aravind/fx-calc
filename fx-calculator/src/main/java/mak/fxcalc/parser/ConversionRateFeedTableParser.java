package mak.fxcalc.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mak.fxcalc.util.ParsedObject;

public class ConversionRateFeedTableParser implements IParser<Map<String, Float>>{
	private final Pattern currencyConversionRatePattern;
	public ConversionRateFeedTableParser(Pattern currencyConversionRatePattern) {
		this.currencyConversionRatePattern = currencyConversionRatePattern;
	}
	
	@Override
	public ParsedObject<Map<String, Float>> parseValidatedLines(final List<String> validatedInputLines) {
		final ParsedObject<Map<String, Float>> emptyParsedObject = new ParsedObject<>(Collections.emptyMap());
		if (validatedInputLines==null || validatedInputLines.isEmpty()) return emptyParsedObject;
		final Map<String, Float> conversionRateFeedLookUpMap = getConversionRateFeedLookUpMap(validatedInputLines);
		final ParsedObject<Map<String, Float>> parsedObject = new ParsedObject<>(conversionRateFeedLookUpMap);
        return parsedObject;
	}
	
	private Map<String, Float> getConversionRateFeedLookUpMap(final List<String> validatedInputLines){
		final Map<String, Float> straightConversionRateMap = validatedInputLines.stream()
		   		  		   												  		.map(line -> this.currencyConversionRatePattern.matcher(line))
		   		  		   												  		.filter(matcher -> matcher.find())
		   		  		   												  		.collect(Collectors.toMap(matcher -> getBaseTermCurrenciesComboKey(matcher), 
		   		  		   														  						  matcher -> getConversionRate(matcher)
		   		  		   														  						  )
		   		  		   												  				);
		
		final Map<String, Float> invertedConversionRateMap = validatedInputLines.stream()
				  																.map(line -> this.currencyConversionRatePattern.matcher(line))
				  																.filter(matcher -> matcher.find())
				  																.collect(Collectors.toMap(matcher -> getInvertedCurrenciesComboKey(matcher), 
				  																						  matcher -> getInvertedConverionRate(matcher)
				  																						  )
				  																		);
		
		final Map<String, Float> conversionRateFeedLookUpMap = Stream.of(straightConversionRateMap,invertedConversionRateMap)
																	 .map(Map::entrySet)
																	 .flatMap(Collection::stream)
																	 .collect(Collectors.toMap(Map.Entry::getKey,
																			 				   Map.Entry::getValue
																			 				   )
																			 );
		
		return Collections.unmodifiableMap(conversionRateFeedLookUpMap);
		
	}
	
	private Float getConversionRate(final Matcher matcher){
		return Float.parseFloat(matcher.group(8));
	}
	
	private Float getInvertedConverionRate(final Matcher matcher){
		final Float conversionRate=Float.parseFloat(matcher.group(8));
		final Float invertedConverionRate = Math.round ((1F/conversionRate) * 10000.0f) / 10000.0f;
		return invertedConverionRate;
	}
	
	private String getBaseTermCurrenciesComboKey(final Matcher matcher){
		return getParsedBaseCurrency(matcher).concat(getParsedTermCurrency(matcher));
	}
	
	private String getInvertedCurrenciesComboKey(final Matcher matcher){
		return getParsedTermCurrency(matcher).concat(getParsedBaseCurrency(matcher));
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