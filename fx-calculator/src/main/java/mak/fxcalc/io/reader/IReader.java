package mak.fxcalc.io.reader;

import java.io.Reader;
import java.util.List;

public interface IReader {
	public List<String> getValidatedInputLines();
	public void setReader(Reader fileReader);
}