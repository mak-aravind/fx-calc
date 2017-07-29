package mak.fxcalc.io.validator;



import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import mak.fxcalc.io.reader.IReader;
import mak.fxcalc.io.reader.InputReader;

public class UserInputCommandValidator implements IUserInputValidator{

	final private IReader commandReader;
	
	public UserInputCommandValidator(Pattern pattern) {
		this.commandReader = new InputReader(pattern);
	}

	@Override
	public List<String> getValidatedInputLines(final String command) throws IOException{
		if (command == null || command.isEmpty()) return emptyList();
		List<String> validatedInputLines = emptyList();
		try(final Reader inputReader = new StringReader(command)){
			commandReader.setReader(inputReader);
			validatedInputLines = commandReader.getValidatedInputLines();
		}
		return validatedInputLines.isEmpty() ?  Collections.emptyList() : validatedInputLines;
	}

}