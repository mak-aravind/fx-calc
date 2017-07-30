package mak.fxcalc.registry;



import static mak.fxcalc.app.config.TestFileName.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME;
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME;
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME;

import mak.fxcalc.cache.FileConfig;
import mak.fxcalc.cache.FileContentsCache;
import mak.fxcalc.cache.FilePatterns;

public class RegistryTestConfig {
	
	final static FileConfig FILE_CONFIG = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
			VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
			VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME);
	
	final static FilePatterns FILE_PATTERNS = new FilePatterns(FILE_CONFIG);
	final static FileContentsCache FILE_CONTENTS_CACHE = new FileContentsCache(FILE_PATTERNS);
}
