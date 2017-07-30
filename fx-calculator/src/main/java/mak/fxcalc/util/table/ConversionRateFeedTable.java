package mak.fxcalc.util.table;

import static mak.fxcalc.app.config.ParserConfig.CONVERSION_RATE_FEED_TABLE_PARSER;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mak.fxcalc.parser.IParser;
import mak.fxcalc.parser.ParserFactory;
import mak.fxcalc.util.ParsedObject;

public class ConversionRateFeedTable{
	private final Map<String, Float> conversionRateFeedTableMap;
	
	private ConversionRateFeedTable(Map<String, Float> conversionRateFeedTableMap) {
		this.conversionRateFeedTableMap = conversionRateFeedTableMap;
	}
	
	public static ConversionRateFeedTable createConversionRateFeedLookUp(List<String> conversionRateFeedLines) {
		@SuppressWarnings("unchecked")
		final IParser<Map<String, Float>> conversionRateFeedTableParser = ParserFactory.getParser(CONVERSION_RATE_FEED_TABLE_PARSER);
		ParsedObject<Map<String, Float>> parsedObject = conversionRateFeedTableParser.parseValidatedLines(conversionRateFeedLines);
		Map<String, Float> conversionRateFeedTableMap = parsedObject.getTableData();
		return (conversionRateFeedTableMap == Collections.EMPTY_MAP) 
				? null : new ConversionRateFeedTable(conversionRateFeedTableMap);
	}
	
	public Float getConversionRate(String currenciesComboKey){
		return conversionRateFeedTableMap.get(currenciesComboKey);
	}
	
	public boolean contains(String currencyCombo){
		return conversionRateFeedTableMap.containsKey(currencyCombo);
	}
}