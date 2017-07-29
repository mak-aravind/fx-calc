package mak.fxcalc.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mak.fxcalc.io.validator.UserInputFileValidator;

public class FileContentsCache {
	
	final Map<String, Pattern> patternsMappedToFileName;
	final private FilePatterns filePatterns;
	final private Map<String, List<String>> listCachedWithFileContents = new HashMap<>();
	final private boolean empty;
	
	public FileContentsCache(FilePatterns filePatterns){
		this.filePatterns = filePatterns;
		this.patternsMappedToFileName = this.filePatterns.getPatternsMappedToFileName();
		this.empty = loadFileContents();
	}
	public FilePatterns getFilePatterns() {
		return filePatterns;
	}
	
	public boolean isEmpty(){
		return this.empty;
	}
	
	public List<String> getCachedFileContentsAsList(String fileName){
		return listCachedWithFileContents.get(fileName);
	}
	
	private boolean loadFileContents(){
		final List<String> listOfFileName = new ArrayList<String>(patternsMappedToFileName.keySet());
		for (String fileName : listOfFileName) {
			final List<String> validatedInputLines = getValidatedInputLines(fileName);
			if (null == validatedInputLines || validatedInputLines.isEmpty()){
					return true;
			}
			listCachedWithFileContents.put(fileName, validatedInputLines);
		}
		return false;
	}

	private List<String> getValidatedInputLines(String fileName){
		final Pattern pattern = patternsMappedToFileName.get(fileName);
		final UserInputFileValidator userInputFileValidator = new UserInputFileValidator(pattern);
		List<String> validatedInputLines;
		try {
			validatedInputLines = userInputFileValidator.getValidatedInputLines(fileName);
		} catch (IOException e) {
			validatedInputLines = Collections.emptyList();
			System.out.println("<FX-CALCULATOR>Unexpected IO exception: " + e.getMessage());
		} 
		return validatedInputLines;
	}
}