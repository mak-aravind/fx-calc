package mak.fxcalc.app.config;

public class RegularExpressionConfig {
	
	/*private RegularExpressionConfig(){
		throw new AssertionError();//Big Bang when instantiated from inside class(locally)
	}*/
	public static final int NUMBER_OF_CURRENCIES_SUPPORTED = 11;
	
	public static final String CROSS_CURRENCY_EACH_LINE_REGEX = "^[a-zA-Z]{3}(\\,[a-zA-Z]{3}){"+NUMBER_OF_CURRENCIES_SUPPORTED+"}$";
	public static final String CURRENCY_CONVERSION_RATE_REGEX = "([a-z])([a-z])([a-z])([a-z])([a-z])([a-z])(=)([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";
	public static final String CURRENCY_DECIMAL_PLACES_REGEX = "([a-z])([a-z])([a-z])(=)(\\d+)";
	public static final String CSV_STRIPPING_REGEX = "\\s*,\\s*";
}