package mak.fxcalc.io.reader;



import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.regex.Pattern;

import mak.fxcalc.io.util.DefaultFileUtil;
import mak.fxcalc.io.validator.IValidator;
import mak.fxcalc.io.validator.InputValidator;

public class UserInputFileReader implements IDefaultUserInputReader{

	final private IValidator fileValidator;
	
	public UserInputFileReader(Pattern pattern) {
		this.fileValidator = new InputValidator(pattern);
	}

	@Override
	public Reader getUserInputReader(String input) {
		Reader inputReader = null;
		try {
			inputReader = DefaultFileUtil.getInputStreamReader(input);
		} catch (FileNotFoundException e) {
				System.out.println("File not found please rerun with valid file name.");
				return null;
		}
		return inputReader;
	}
	@Override
	public IValidator getValidator() {
		return this.fileValidator;
	}
}