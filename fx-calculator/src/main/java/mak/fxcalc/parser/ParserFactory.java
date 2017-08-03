package mak.fxcalc.parser;

import static mak.fxcalc.app.config.FileInputReaderConfig.CSV_STRIPPING_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_DECIMAL_PLACES_PATTERN;
import static mak.fxcalc.app.config.ParserConfig.*;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {
	@SuppressWarnings("rawtypes")
	private final Map<String,IParser> parsersList;

	private ParserFactory(){
		this.parsersList = new HashMap<>();
		this.parsersList.put(CROSS_CURRENCY_TABLE_PARSER, new CrossCurrencyTableParser(CSV_STRIPPING_PATTERN));
		this.parsersList.put(CONVERSION_RATE_FEED_TABLE_PARSER, new ConversionRateFeedTableParser(CURRENCY_CONVERSION_RATE_PATTERN));
		this.parsersList.put(CURRENCY_DECIMAL_LOOK_UP_PARSER, new CurrencyDecimalLookUpParser(CURRENCY_DECIMAL_PLACES_PATTERN));
		this.parsersList.put(CURRENCY_INDEX_LOOK_UP_PARSER, new CurrencyIndexLookUpParser(CSV_STRIPPING_PATTERN));
	}
	
	@SuppressWarnings("rawtypes")
	public static IParser getParser(String requiredParser){
		ParserFactory parserFactory = new ParserFactory();
		return parserFactory.get(requiredParser);
	}

	@SuppressWarnings("rawtypes")
	private IParser get(String requiredParser) {
		return this.parsersList.get(requiredParser);
	}
}