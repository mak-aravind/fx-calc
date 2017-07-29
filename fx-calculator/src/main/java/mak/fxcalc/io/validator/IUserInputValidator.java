package mak.fxcalc.io.validator;

import java.io.IOException;
import java.util.List;

public interface IUserInputValidator {
	public List<String> getValidatedInputLines(final String fileName) throws IOException;
}