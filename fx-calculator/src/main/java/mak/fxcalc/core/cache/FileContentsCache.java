package mak.fxcalc.core.cache;

import static mak.fxcalc.app.config.FileConfig.VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME;
import static mak.fxcalc.app.config.FileConfig.VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME;
import static mak.fxcalc.app.config.FileConfig.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME;
import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_DECIMAL_PLACES_PATTERN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mak.fxcalc.io.validator.UserInputFileValidator;

public class FileContentsCache {
	
	final Map<String, Pattern> patternsMappedToFileName=new HashMap<>();
	final Map<String, List<String>> listCachedWithFileContents = new HashMap<>();
	final private boolean empty;
	
	public FileContentsCache(){
		this.patternsMappedToFileName.put(VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME, CROSS_CURRENCY_EACH_LINE_PATTERN);
		this.patternsMappedToFileName.put(VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME, CURRENCY_DECIMAL_PLACES_PATTERN);
		this.patternsMappedToFileName.put(VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME, CURRENCY_CONVERSION_RATE_PATTERN);
		this.empty = loadFileContents();
	}
	
	public boolean isEmpty(){
		return this.empty;
	}
	
	public List<String> getCachedFileContentsAsList(String fileName){
		return listCachedWithFileContents.get(fileName);
	}
	
	private boolean loadFileContents() {
		final List<String> listOfFileName = new ArrayList<String>(patternsMappedToFileName.keySet());
		for (String fileName : listOfFileName) {
			List<String> validatedInputLines = getValidatedInputLines(fileName);
			if (null == validatedInputLines || validatedInputLines.isEmpty())
				return true;
			listCachedWithFileContents.put(fileName, validatedInputLines);
		}
		return false;
	}

	private List<String> getValidatedInputLines(String fileName) {
		final Pattern pattern = patternsMappedToFileName.get(fileName);
		final UserInputFileValidator userInputFileValidator = new UserInputFileValidator(pattern);
		return userInputFileValidator.getValidatedInputLines(fileName);
	}
}