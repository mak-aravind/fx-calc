package mak.fxcalc.io.validator;

import java.io.Reader;
import java.util.List;

public interface IValidator {
	public List<String> getValidatedInputLines();
	public void setReader(final Reader fileReader);
}