package mak.fxcalc.io.validator;



import static java.util.Collections.emptyList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import mak.fxcalc.io.reader.IReader;
import mak.fxcalc.io.reader.InputReader;
import mak.fxcalc.io.util.DefaultFileUtil;

public class UserInputFileValidator implements IUserInputValidator{

	final private IReader fileReader;
	
	public UserInputFileValidator(Pattern pattern) {
		this.fileReader = new InputReader(pattern);
	}
	@Override
	public List<String> getValidatedInputLines(final String fileName) throws IOException {
		if (fileName == null || fileName.isEmpty()) return emptyList();
		List<String> validatedInputLines = emptyList();
		try(final Reader inputReader = getInputReader(fileName)){
			if (null == inputReader)
				return emptyList();
			fileReader.setReader(inputReader);
			validatedInputLines = fileReader.getValidatedInputLines();
		}
		return validatedInputLines.isEmpty() ?  Collections.emptyList() : validatedInputLines;
	}

	private Reader getInputReader(final String fileName) {
		Reader inputReader = null;
		try {
			inputReader = DefaultFileUtil.getInputStreamReader(fileName);
		} catch (FileNotFoundException e) {
				System.out.println("File not found Please rerun with valid file name.");
				return null;
		}
		return inputReader;
	}
}