package mak.fxcalc.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import mak.fxcalc.io.reader.IDefaultUserInputReader;
import mak.fxcalc.io.reader.UserInputFileReader;

public class FileContentsCache {
	
	final Map<String, Pattern> patternsMappedToFileName;
	final private FilePatterns filePatterns;
	final private Map<String, List<String>> listCachedWithFileContents;
	
	public FileContentsCache(final FilePatterns filePatterns){
		this.filePatterns = filePatterns;
		this.patternsMappedToFileName = this.filePatterns.getPatternsMappedToFileName();
		this.listCachedWithFileContents = loadFileContents();
	}
	public FilePatterns getFilePatterns() {
		return this.filePatterns;
	}
	
	public boolean isEmpty(){
		return null == this.listCachedWithFileContents;
	}
	
	public List<String> getCachedFileContentsAsList(String fileName){
		return this.listCachedWithFileContents.get(fileName);
	}
	
	private Map<String, List<String>> loadFileContents(){
		final List<String> listOfFileName = new ArrayList<String>(this.patternsMappedToFileName.keySet());
		if (listOfFileName.stream()
						  .map(fileName -> getValidatedInputLines(fileName))
						  .anyMatch(validatedInputLines -> validatedInputLines.isEmpty()))
			return null;
		
		final Map<String, List<String>> fileNameMappedToValidatedLines = listOfFileName.stream()
																				 	   .collect(
																				 			   Collectors.toMap(String::toString,
																								 		  		fileName -> getValidatedInputLines(fileName)
																								 		 	   )
																				 			   );
		return Collections.unmodifiableMap(fileNameMappedToValidatedLines);
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