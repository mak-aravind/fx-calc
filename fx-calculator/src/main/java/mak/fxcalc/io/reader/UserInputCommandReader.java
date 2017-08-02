package mak.fxcalc.io.reader;

import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

import mak.fxcalc.io.validator.IValidator;
import mak.fxcalc.io.validator.InputValidator;

public class UserInputCommandReader implements IDefaultUserInputReader {
	
	final private IValidator commandValidator;
	
	public UserInputCommandReader(Pattern pattern) {
		this.commandValidator = new InputValidator(pattern);
	}

	@Override
	public Reader getUserInputReader(String input) {
		return new StringReader(input);
	}

	@Override
	public IValidator getValidator() {
		return this.commandValidator;
	}

}
