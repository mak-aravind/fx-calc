package mak.fxcalc.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mak.fxcalc.io.reader.IDefaultUserInputReader;
import mak.fxcalc.io.reader.UserInputFileReader;

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
		return this.listCachedWithFileContents.get(fileName);
	}
	
	private boolean loadFileContents(){
		final List<String> listOfFileName = new ArrayList<String>(patternsMappedToFileName.keySet());
		for (String fileName : listOfFileName) {
			final List<String> validatedInputLines = getValidatedInputLines(fileName);
			if (null == validatedInputLines || validatedInputLines.isEmpty()){
					return true;
			}
			this.listCachedWithFileContents.put(fileName, validatedInputLines);
		}
		return false;
	}

	private List<String> getValidatedInputLines(String fileName){
		final Pattern pattern = this.patternsMappedToFileName.get(fileName);
		final IDefaultUserInputReader userInputFileReader = new UserInputFileReader(pattern);
		try {
			return userInputFileReader.getValidatedInputLines(fileName);
		} catch (IOException e) {
			System.out.println("<FX-CALCULATOR>Unexpected IO exception: " + e.getMessage());
			return Collections.emptyList();
		} 
	}
}