package mak.fxcalc.parser

import java.util.List

import mak.fxcalc.io.reader.IDefaultUserInputReader
import mak.fxcalc.io.reader.UserInputFileReader
import mak.fxcalc.io.validator.InputValidator
import mak.fxcalc.parser.CurrencyDecimalLookUpParser
import spock.lang.Specification

import static java.util.Collections.EMPTY_MAP
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_DECIMAL_PLACES_PATTERN
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME

class CurrencyDecimalLookUpParserSpec extends Specification{

	def validator = new InputValidator(CURRENCY_DECIMAL_PLACES_PATTERN)
	def CurrencyDecimalLookUpParser = new CurrencyDecimalLookUpParser(CURRENCY_DECIMAL_PLACES_PATTERN);
	def IDefaultUserInputReader fileReader = new UserInputFileReader(CURRENCY_DECIMAL_PLACES_PATTERN)
	
	def "Each line should have int value to right side of equal sign"(String currencyDecimalPlacesLine,Map result){
		given:
			def stringReader = new StringReader(currencyDecimalPlacesLine)
			validator.setReader(stringReader)
			def parsedObject = CurrencyDecimalLookUpParser.parseValidatedLines(validator.getValidatedInputLines())
		expect:
			result == parsedObject.getTableData()
		where:
			currencyDecimalPlacesLine	|result
			"GBP="						|EMPTY_MAP
			"GBP=2.0"					|EMPTY_MAP
	}
	
	def "Look for key EUR and get 2 as value" (String currencyDecimalPlacesLine,String key, int value){
		given:
			def stringReader = new StringReader(currencyDecimalPlacesLine)
			validator.setReader(stringReader)
			def parsedObject = CurrencyDecimalLookUpParser.parseValidatedLines(validator.getValidatedInputLines())
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			currencyDecimalPlacesLine	|key		|value
			"EUR=2"						|"EUR"		|2
	}
	
	def "File with 11 lines of valid entries should return list of size 11"(){
		given:
			def validInputLines = fileReader.getValidatedInputLines(VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
		when:
			def parsedObject = CurrencyDecimalLookUpParser.parseValidatedLines(validInputLines)
			def result = parsedObject.getTableData()
		then:
			result.size() == 11
	}
	
	def "File with 11 lines. 11 valid entries and 3 invalid entry should return empty map"(){
		given:
			def validInputLines = fileReader.getValidatedInputLines(INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
		when:
			def parsedObject = CurrencyDecimalLookUpParser.parseValidatedLines(validInputLines)
			def result = parsedObject.getTableData()
		then:
			result == EMPTY_MAP
	}
}
