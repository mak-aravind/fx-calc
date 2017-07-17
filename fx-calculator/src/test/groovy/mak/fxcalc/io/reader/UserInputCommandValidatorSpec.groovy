package mak.fxcalc.io.reader

import java.util.List

import mak.fxcalc.io.validator.UserInputCommandValidator

import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.CommandInputReaderConfig.INPUT_COMMAND_PATTERN
import static mak.fxcalc.app.config.TestFileConfig.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileConfig.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME

import spock.lang.Specification

class UserInputCommandValidatorSpec extends Specification{
	def inputReader = new UserInputCommandValidator(INPUT_COMMAND_PATTERN)
	
	def "Command should be start with three character"(String inputCurrencyConversionCommand,List result){
		expect:
			inputReader.getValidatedInputLines(inputCurrencyConversionCommand) == result
		where:
			inputCurrencyConversionCommand	|result
			"UD 100.00 in USD"				|EMPTY_LIST
			"D 100.00 in USD"				|EMPTY_LIST
			""								|EMPTY_LIST
			"AUD 100.00 in DKK"				|["AUD 100.00 IN DKK"]
	}
	
	def "After base currency(3 characters) an int or float value is allowed as amount"(String inputCurrencyConversionCommand,List result){
		expect:
			inputReader.getValidatedInputLines(inputCurrencyConversionCommand) == result
		where:
			inputCurrencyConversionCommand	|result
			"AUD OOO in USD"				|EMPTY_LIST
			"AUD aa in USD"					|EMPTY_LIST
			"AUD in USD"					|EMPTY_LIST
			"AUD 100 in USD"				|["AUD 100 IN USD"]
			"AUD 000 in USD"				|["AUD 000 IN USD"]
			"AUD 100.00 in USD"				|["AUD 100.00 IN USD"]
	}
	
	def "After amount the term currency(3 characters) should be preceded by word - in "(String inputCurrencyConversionCommand,List result){
		expect:
			inputReader.getValidatedInputLines(inputCurrencyConversionCommand) == result
		where:
			inputCurrencyConversionCommand	|result
			"AUD 100.00 USD"				|EMPTY_LIST
			"AUD 100.00 in SD"				|EMPTY_LIST
			"AUD 100.00 inUSD"				|EMPTY_LIST
			"AUD 100.00 USD"				|EMPTY_LIST
			"JPY 100 in USD"				|["JPY 100 IN USD"]
			"JPY 0 in USD"					|["JPY 0 IN USD"]
	}
}
