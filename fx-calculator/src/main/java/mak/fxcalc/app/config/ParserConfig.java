package mak.fxcalc.app.config;

public class ParserConfig {
	/*private File(){
	throw new AssertionError();//Big Bang when instantiated from inside class(locally)
}*/
	public ParserConfig(){}
	
	public static final String CURRENCY_INDEX_LOOK_UP_PARSER = "currencyIndexLookUpParser";
	public static final String CURRENCY_DECIMAL_LOOK_UP_PARSER = "currencyDecimalLookUpParser";
	public static final String CONVERSION_RATE_FEED_TABLE_PARSER = "conversionRateFeedTableParser";
	public static final String CROSS_CURRENCY_TABLE_PARSER = "crossCurrencyTableParser";
}