package mak.fxcalc.cache

import static mak.fxcalc.app.config.TestFileName.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME

import static mak.fxcalc.app.config.TestFileName.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME

import mak.fxcalc.registry.FxCalculatorRegistry
import spock.lang.Specification

class FileContentsCacheSpec extends Specification{
	
	def "File Config with ANY invalid file contents should create empty fileContents cache"(){
		given:
			def FileConfig invalidFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
																INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME,
																VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME);
			
			def FilePatterns filePatterns = new FilePatterns(invalidFileConfig);
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns) 
		expect:
			true == fileContentsCache.isEmpty()
	}
	
	def "File Config with ALL valid file contents should NOT create empty fileContents cache"(){
		given:
			def FileConfig validFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
																VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
																VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME);
			
			def FilePatterns filePatterns = new FilePatterns(validFileConfig);
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
		expect:
			false == fileContentsCache.isEmpty()
	}
	
	def "File Config with ANY invalid file contents should create a null FxCalculatorRegistry"(){
		given:
			def FileConfig invalidFileConfig = new FileConfig(INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
																VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
																VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME);
			
			def FilePatterns filePatterns = new FilePatterns(invalidFileConfig);
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
			def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(fileContentsCache)
		expect:
			null == fxCalculatorRegistry
	}
	
	def "File Config with ALL valid file contents should build a non-null FxCalculatorRegistry"(){
		given:
			def FileConfig validFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
																VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
																VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME);
			
			def FilePatterns filePatterns = new FilePatterns(validFileConfig);
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
			def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(fileContentsCache)
		expect:
			null != fxCalculatorRegistry
			false == fxCalculatorRegistry.isEmptyRegistry()
	}
}
