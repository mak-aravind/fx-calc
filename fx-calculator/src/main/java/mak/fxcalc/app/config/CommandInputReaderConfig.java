package mak.fxcalc.app.config;

import java.util.regex.Pattern;

public class CommandInputReaderConfig {
	
	private CommandInputReaderConfig(){
		throw new AssertionError();//Big Bang when instantiated from inside class(locally)
	}
	private static final int PATTERN_COMPILATION_CONFIG = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	private static final String currency = "([a-z])([a-z])([a-z])";
    private static final String singleWhiteSpace="(\\s+)";
    private static final String floatOrInteger="(\\d+(?:\\.\\d{2})?)";
    private static final String inWord="(in)";
    //AUD 100.00 in DKK
    private static final String INPUT_COMMAND_REGEX = currency+singleWhiteSpace+floatOrInteger+singleWhiteSpace+inWord+singleWhiteSpace+currency;
	public static final Pattern INPUT_COMMAND_PATTERN = Pattern.compile(INPUT_COMMAND_REGEX,PATTERN_COMPILATION_CONFIG);
	public static final String INVALID_COMMAND = "Invalid Command";
	public static final String EXAMPLE_COMMAND = "Example: AUD 100.00 in DKK";
}