package mak.fxcalc.io.reader;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import mak.fxcalc.io.validator.IValidator;

public interface IDefaultUserInputReader {
	public Reader getUserInputReader(String input);
	public IValidator getValidator();
	
	default public List<String> getValidatedInputLines(final String input) throws IOException {
		if (input == null || input.isEmpty()) return emptyList();
		try(final Reader inputReader = this.getUserInputReader(input)){
			if (null == inputReader) return emptyList();
			this.getValidator().setReader(inputReader);
			final List<String> validatedInputLines = this.getValidator().getValidatedInputLines();
			return validatedInputLines.isEmpty() ? Collections.emptyList() : validatedInputLines;
		}
	}
}