package mak.fxcalc.cache;

import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN;
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_DECIMAL_PLACES_PATTERN;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FilePatterns{
	private final Map<String, Pattern> patternsMappedToFileName;
	private final FileConfig fileConfig;
	public FilePatterns(FileConfig fileConfig){
		this.fileConfig = fileConfig;
		this.patternsMappedToFileName = new HashMap<>();
		this.patternsMappedToFileName.put(this.fileConfig.getCrossCurrencyMatrixFileName(), CROSS_CURRENCY_EACH_LINE_PATTERN);
		this.patternsMappedToFileName.put(this.fileConfig.getCurrencyDecimalPlacesFileName(), CURRENCY_DECIMAL_PLACES_PATTERN);
		this.patternsMappedToFileName.put(this.fileConfig.getCurrencyRatesFileName(), CURRENCY_CONVERSION_RATE_PATTERN);
	}

	public Map<String, Pattern> getPatternsMappedToFileName() {
		return Collections.unmodifiableMap(this.patternsMappedToFileName);
	}

	public FileConfig getFileConfig() {
		return this.fileConfig;
	}
}