package mak.fxcalc.app.config;

import java.util.regex.Pattern;
import static mak.fxcalc.app.config.RegularExpressionConfig.*;

public class FileInputReaderConfig {
	
	private FileInputReaderConfig(){
		throw new AssertionError();//Big Bang when instantiated from inside class(locally)
	}
	private static final int PATTERN_COMPILATION_CONFIG = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	public static final Pattern CURRENCY_CONVERSION_RATE_PATTERN = Pattern.compile(CURRENCY_CONVERSION_RATE_REGEX,PATTERN_COMPILATION_CONFIG);
	public static final Pattern CROSS_CURRENCY_EACH_LINE_PATTERN = Pattern.compile(CROSS_CURRENCY_EACH_LINE_REGEX,PATTERN_COMPILATION_CONFIG);
	public static final Pattern CURRENCY_DECIMAL_PLACES_PATTERN = Pattern.compile(CURRENCY_DECIMAL_PLACES_REGEX,PATTERN_COMPILATION_CONFIG);
	public static final Pattern CSV_STRIPPING_PATTERN = Pattern.compile(CSV_STRIPPING_REGEX,PATTERN_COMPILATION_CONFIG);
}