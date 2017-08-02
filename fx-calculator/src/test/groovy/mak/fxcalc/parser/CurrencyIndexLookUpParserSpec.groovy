package mak.fxcalc.parser

import java.util.List

import mak.fxcalc.io.validator.InputValidator
import mak.fxcalc.parser.CurrencyIndexLookUpParser
import spock.lang.Specification

import static java.util.Collections.EMPTY_MAP
import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN
import static mak.fxcalc.app.config.FileInputReaderConfig.CSV_STRIPPING_PATTERN

class CurrencyIndexLookUpParserSpec extends Specification{
	def inputValidator = new InputValidator(CROSS_CURRENCY_EACH_LINE_PATTERN)
	def CurrencyIndexLookUpParser = new CurrencyIndexLookUpParser(CSV_STRIPPING_PATTERN);
	def "Line with 11 entries should return empty map"(String crossCurrencyLine,Map result){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputValidator.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputValidator.getValidatedInputLines())
		expect:
			result == parsedObject.getTableData()
		where:
			crossCurrencyLine								|result
			"XXX,AUD,CAD,CNY,CZK,EUR,GBP,JPY,NOK,NZD,USD"	|EMPTY_MAP
	}
	
	def "Line with 12 entries should return map"(String crossCurrencyLine,int size){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputValidator.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputValidator.getValidatedInputLines())
			def result = parsedObject.getTableData();
		expect:
			result.size() == size
		where:
			crossCurrencyLine									|size
			"XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD"	|12
	}
	
	def "Line with 12 entries and for key CZK should return 4"(String crossCurrencyLine,String key,int value){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputValidator.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputValidator.getValidatedInputLines())
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			crossCurrencyLine									|key	|value
			"XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD"	|"CZK"	|4
	}
}
