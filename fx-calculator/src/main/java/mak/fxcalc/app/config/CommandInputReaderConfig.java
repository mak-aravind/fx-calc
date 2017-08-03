package mak.fxcalc.app.config;

import java.util.regex.Pattern;
import static mak.fxcalc.app.config.RegularExpressionConfig.INPUT_COMMAND_REGEX;

public class CommandInputReaderConfig {
	
	private CommandInputReaderConfig(){
		throw new AssertionError();//Big Bang when instantiated from inside class(locally)
	}
	private static final int PATTERN_COMPILATION_CONFIG = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	public static final Pattern INPUT_COMMAND_PATTERN = Pattern.compile(INPUT_COMMAND_REGEX,PATTERN_COMPILATION_CONFIG);
	public static final String INVALID_COMMAND = "Invalid Command";
	public static final String EXAMPLE_COMMAND = "Example: AUD 100.00 in DKK";
}