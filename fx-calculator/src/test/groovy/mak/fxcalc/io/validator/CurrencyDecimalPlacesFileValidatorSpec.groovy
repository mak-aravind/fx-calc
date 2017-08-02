package mak.fxcalc.io.validator

import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_DECIMAL_PLACES_PATTERN
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME


import java.util.List

import mak.fxcalc.io.reader.IDefaultUserInputReader
import mak.fxcalc.io.reader.UserInputFileReader
import mak.fxcalc.io.validator.InputValidator
import spock.lang.Specification

class CurrencyDecimalPlacesFileValidatorSpec extends Specification{
	
	def inputValidator = new InputValidator(CURRENCY_DECIMAL_PLACES_PATTERN)
	def IDefaultUserInputReader fileReader = new UserInputFileReader(CURRENCY_DECIMAL_PLACES_PATTERN)
	
	def "Each line should have three characters to left side of ="(String currencyDecimalPlacesLine,List result){
		given:
			def stringReader = new StringReader(currencyDecimalPlacesLine)
			inputValidator.setReader(stringReader)
		expect:
			inputValidator.getValidatedInputLines() == result
		where:
			currencyDecimalPlacesLine	|result
			"AD=2"						|EMPTY_LIST
			"AUSD=2"					|EMPTY_LIST
			"AUD=2"						|["AUD=2"]
	}
	def "Each line should have equal sign after three characters"(String currencyDecimalPlacesLine,List result){
		given:
			def stringReader = new StringReader(currencyDecimalPlacesLine)
			inputValidator.setReader(stringReader)
		expect:
			inputValidator.getValidatedInputLines() == result
		where:
			currencyDecimalPlacesLine	|result
			"AUD2"						|EMPTY_LIST
			"GBP=2"						|["GBP=2"]
	}
	
	def "Each line should have int value to right side of equal sign"(String currencyDecimalPlacesLine,List result){
		given:
			def stringReader = new StringReader(currencyDecimalPlacesLine)
			inputValidator.setReader(stringReader)
		expect:
			inputValidator.getValidatedInputLines() == result
		where:
			currencyDecimalPlacesLine	|result
			"GBP="						|EMPTY_LIST
			"GBP=2.0"					|EMPTY_LIST
			"JPY=0"						|["JPY=0"]
	}
	
	def "File with 11 lines of valid entries should return list of size 11"(){
		when:
			def result = fileReader.getValidatedInputLines(VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
		then:
			result.size() == 11
	}
	
	def "File with 11 lines. 11 valid entries and 3 invalid entry should return empty list"(){
		when:
			def result = fileReader.getValidatedInputLines(INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
		then:
			result == EMPTY_LIST
	}
}
