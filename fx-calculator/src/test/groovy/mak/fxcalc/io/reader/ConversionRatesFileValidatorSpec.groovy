package mak.fxcalc.io.reader

import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN
import static mak.fxcalc.app.config.TestFileConfig.VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME

import mak.fxcalc.io.validator.UserInputFileValidator

import static mak.fxcalc.app.config.TestFileConfig.INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME

import spock.lang.Specification


class ConversionRatesFileValidatorSpec extends Specification{
	def inputReader = new InputReader(CURRENCY_CONVERSION_RATE_PATTERN)
	
	def "Single line should only have six alphabets to left side of equal sign"(String currencyConversionRate,List result){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			currencyConversionRate	|result
			"1UDUSD=0.8371"	   	  	|EMPTY_LIST
			"dfdudct=0.6457"		|EMPTY_LIST
			"aU45SD=0.8371"			|EMPTY_LIST
			"123456=0.8371"			|EMPTY_LIST
			"AUDUSD=0.8371"			|["AUDUSD=0.8371"]
	}
	
	def "Single line should start with six alphabets followed by equal sign"(String currencyConversionRate,List result){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			currencyConversionRate		|result
			"AUDUSD==0.8371"	   	  	|EMPTY_LIST
			"AUDUSD|0.8371"	   	  		|EMPTY_LIST
			"AUDUSD0.8371"	   	  		|EMPTY_LIST
			"AUDUSD=0.8371"	   	  		|["AUDUSD=0.8371"]
	}
	
	def "Single line should only have floating point number to right side of equal sign"(String currencyConversionRate,List result){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			currencyConversionRate	|result
			"AUDUSD=8371"	   	  	|EMPTY_LIST
			"AUDUSD=0.8371"	   	  	|["AUDUSD=0.8371"]
	}

	def "Single line if it contains lowercase char it has to CAPATALIZE"(String currencyConversionRate,List result){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			currencyConversionRate	|result
			"aUDUSD=0.8371"			|["AUDUSD=0.8371"]
			"USdjPY=119.95"			|["USDJPY=119.95"]
			"eurnok=8.6651"			|["EURNOK=8.6651"]
	}
	
	def "File with 10 lines of valid entries should return list of size 10"(){
		given:
			def validator = new UserInputFileValidator(CURRENCY_CONVERSION_RATE_PATTERN);
		when:
			def result = validator.getValidatedInputLines(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME)
		then:
			result.size() == 10
	}
	
	def "File with 10 lines. 7 valid entries and 3 invalid entries should return empty list"(){
		given:
			def validator = new UserInputFileValidator(CURRENCY_CONVERSION_RATE_PATTERN);
		when:
			def result = validator.getValidatedInputLines(INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME)
		then:
			result == EMPTY_LIST
	}
}
