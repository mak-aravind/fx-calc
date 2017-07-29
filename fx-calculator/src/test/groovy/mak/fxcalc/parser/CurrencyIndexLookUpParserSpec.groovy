package mak.fxcalc.parser

import java.util.List

import mak.fxcalc.io.reader.InputReader
import mak.fxcalc.parser.CurrencyIndexLookUpParser
import spock.lang.Specification

import static java.util.Collections.EMPTY_MAP
import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN
import static mak.fxcalc.app.config.FileInputReaderConfig.CSV_STRIPPING_PATTERN

class CurrencyIndexLookUpParserSpec extends Specification{
	def inputReader = new InputReader(CROSS_CURRENCY_EACH_LINE_PATTERN)
	def CurrencyIndexLookUpParser = new CurrencyIndexLookUpParser(CSV_STRIPPING_PATTERN);
	def "Line with 11 entries should return empty map"(String crossCurrencyLine,Map result){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputReader.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputReader.getValidatedInputLines())
		expect:
			result == parsedObject.getTableData()
		where:
			crossCurrencyLine								|result
			"XXX,AUD,CAD,CNY,CZK,EUR,GBP,JPY,NOK,NZD,USD"	|EMPTY_MAP
	}
	
	def "Line with 12 entries should return map"(String crossCurrencyLine,int size){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputReader.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputReader.getValidatedInputLines())
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
			inputReader.setReader(stringReader)
			def parsedObject = CurrencyIndexLookUpParser.parseValidatedLines(inputReader.getValidatedInputLines())
			def result = parsedObject.getTableData()
		expect:
			result.get(key) == value
		where:
			crossCurrencyLine									|key	|value
			"XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD"	|"CZK"	|4
	}
}
