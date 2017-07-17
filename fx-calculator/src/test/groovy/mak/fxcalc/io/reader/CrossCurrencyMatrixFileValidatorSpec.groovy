package mak.fxcalc.io.reader

import java.util.List

import mak.fxcalc.io.validator.UserInputFileValidator

import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN
import static mak.fxcalc.app.config.TestFileConfig.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileConfig.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME

import spock.lang.Specification

class CrossCurrencyMatrixFileValidatorSpec extends Specification{
	def inputReader = new InputReader(CROSS_CURRENCY_EACH_LINE_PATTERN)
	
	def "Each value should be a three character"(String crossCurrencyLine,List result){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			crossCurrencyLine									|result
			"XXX,AUD,CAD,CNY,CZK,DK,E,GBP,JPY,NOK,NZD,USD"		|EMPTY_LIST
			"XXX,AUD,CAD,CNY,CZK,DKK,EURGBP,JPY,NOK,NZD,USD"	|EMPTY_LIST
			"JPY,USD,USD,USD,USD,USD,USD,USD,OOO,USD,USD,INV"	|["JPY,USD,USD,USD,USD,USD,USD,USD,OOO,USD,USD,INV"]
	}
	
	def "Single line should have 12 comma seperated values"(String crossCurrencyLine,List result){
		given:
			def stringReader = new StringReader(crossCurrencyLine)
			inputReader.setReader(stringReader)
		expect:
			inputReader.getValidatedInputLines() == result
		where:
			crossCurrencyLine									|result
			"XXX,AUD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD" 		|EMPTY_LIST
			",AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD"		|EMPTY_LIST
			"XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD,"	|EMPTY_LIST
			"XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD" 	|["XXX,AUD,CAD,CNY,CZK,DKK,EUR,GBP,JPY,NOK,NZD,USD"]
	}
	
	def "File with 12 lines of valid entries should return list of size 12"(){
		given:
			def validator = new UserInputFileValidator(CROSS_CURRENCY_EACH_LINE_PATTERN);
		when:
			def result = validator.getValidatedInputLines(VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME)
		then:
			result.size() == 12
	}
	
	def "File with 12 lines. 11 valid entries and 3 invalid entry should return empty list"(){
		given:
			def validator = new UserInputFileValidator(CROSS_CURRENCY_EACH_LINE_PATTERN);
		when:
			def result = validator.getValidatedInputLines(INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME)
		then:
			result == EMPTY_LIST
	}
}