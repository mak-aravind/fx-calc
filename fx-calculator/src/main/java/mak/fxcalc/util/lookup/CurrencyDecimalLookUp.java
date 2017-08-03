package mak.fxcalc.util.lookup;

import static mak.fxcalc.app.config.ParserConfig.CURRENCY_DECIMAL_LOOK_UP_PARSER;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mak.fxcalc.parser.IParser;
import mak.fxcalc.parser.ParserFactory;
import mak.fxcalc.util.ParsedObject;

public class CurrencyDecimalLookUp implements ILookUp{
	private final Map<String, Integer> currencyDecimalLookUpMap;
	
	private CurrencyDecimalLookUp(Map<String, Integer> currencyDecimalLookUpMap) {
		this.currencyDecimalLookUpMap = currencyDecimalLookUpMap;
	}
	public static CurrencyDecimalLookUp createCurrencyDecimalLookUp(List<String> currencyDecimalPlacesList) {
		final IParser<Map<String, Integer>> currencyDecimalLookUpParser  = ParserFactory.getParser(CURRENCY_DECIMAL_LOOK_UP_PARSER);
		final ParsedObject<Map<String, Integer>> parsedObject = currencyDecimalLookUpParser.parseValidatedLines(currencyDecimalPlacesList);
		final Map<String, Integer>currencyDecimalLookUpMap = parsedObject.getTableData();
		return Collections.EMPTY_MAP == currencyDecimalLookUpMap ? null : new CurrencyDecimalLookUp(currencyDecimalLookUpMap);
	}
	
	@Override
	public Integer getValue(String currency){
		return this.currencyDecimalLookUpMap.get(currency);
	}
	@Override
	public Boolean contains(String currency) {
		return this.currencyDecimalLookUpMap.containsKey(currency);
	}
}