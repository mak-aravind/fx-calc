package mak.fxcalc.parser

import java.util.List

import mak.fxcalc.io.reader.InputReader
import mak.fxcalc.io.validator.UserInputFileValidator
import mak.fxcalc.parser.ConversionRateFeedTableParser
import spock.lang.Specification

import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.main.FileName.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME


class ConversionRateFeedLookUpParserSpec extends Specification{
	
	def inputReader = new InputReader(CURRENCY_CONVERSION_RATE_PATTERN)
	def ConversionRateFeedTableParser parser = new ConversionRateFeedTableParser(CURRENCY_CONVERSION_RATE_PATTERN);
	
	def "AUDUSD direct AUD/USD value will be 0.8371"(String currencyConversionRate,String key, Float value){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
			def validatedInputLines = inputReader.getValidatedInputLines()
			def parsedObject = parser.parseValidatedLines(validatedInputLines)
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			currencyConversionRate	|key		|value
			"AUDUSD=0.8371"			|"AUDUSD"	|0.8371
	}
	
	def "EURNOK inverted NOK/EUR value will be 0.1154"(String currencyConversionRate,String key, Float value){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
			def validatedInputLines = inputReader.getValidatedInputLines()
			def parsedObject = parser.parseValidatedLines(validatedInputLines)
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			currencyConversionRate	|key		|value
			"EURNOK=8.6651"			|"NOKEUR"	|0.1154		
	}
	
	def "AUDUSD inverted USD/AUD value will be 1.1946"(String currencyConversionRate,String key, Float value){
		given:
			def stringReader = new StringReader(currencyConversionRate)
			inputReader.setReader(stringReader)
			def validatedInputLines = inputReader.getValidatedInputLines()
			def parsedObject = parser.parseValidatedLines(validatedInputLines)
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			currencyConversionRate	|key		|value
			"AUDUSD=0.8371"			|"USDAUD"	|1.1946
	}
	
	def "File with 10 lines of valid entries should return map of size 20"(){
		given:
			def validator = new UserInputFileValidator(CURRENCY_CONVERSION_RATE_PATTERN);
			def validatedInputLines = validator.getValidatedInputLines(VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME)
		when:
			def parsedObject = parser.parseValidatedLines(validatedInputLines)
			def result = parsedObject.getTableData()
		then:
			result.size() == 20
	}
}
