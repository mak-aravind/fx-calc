package mak.fxcalc.util.lookup;

import static mak.fxcalc.app.config.ParserConfig.CURRENCY_INDEX_LOOK_UP_PARSER;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mak.fxcalc.parser.IParser;
import mak.fxcalc.parser.ParserFactory;
import mak.fxcalc.util.ParsedObject;

public class CurrencyIndexLookUp implements ILookUp{
	private final Map<String, Integer> currencyIndexLookUpMap;
	
	private CurrencyIndexLookUp(Map<String, Integer> currencyIndexLookUpMap) {
		this.currencyIndexLookUpMap = currencyIndexLookUpMap;
	}
	public static CurrencyIndexLookUp createCurrencyIndexLookup(List<String> validatedInputLines) {
		final IParser<Map<String, Integer>> currencyIndexLookUpParser = ParserFactory.getParser(CURRENCY_INDEX_LOOK_UP_PARSER);
		final ParsedObject<Map<String, Integer>> parsedObject = currencyIndexLookUpParser.parseValidatedLines(validatedInputLines);
		final Map<String, Integer> currencyIndexLookUpMap = parsedObject.getTableData();
		return currencyIndexLookUpMap == Collections.EMPTY_MAP ? null : new CurrencyIndexLookUp(currencyIndexLookUpMap);
	}
	@Override
	public Integer getValue(String currency){
		return this.currencyIndexLookUpMap.get(currency);
	}
	@Override
	public Boolean contains(String currency) {
		return this.currencyIndexLookUpMap.containsKey(currency);
		
	}
}