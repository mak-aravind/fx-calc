package mak.fxcalc.core.parser

import spock.lang.Specification

import static mak.fxcalc.app.config.FileInputReaderConfig.CROSS_CURRENCY_EACH_LINE_PATTERN
import static mak.fxcalc.app.config.FileInputReaderConfig.CSV_STRIPPING_PATTERN

import mak.fxcalc.io.validator.UserInputFileValidator
import mak.fxcalc.parser.CrossCurrencyTableParser
import mak.fxcalc.parser.CurrencyIndexLookUpParser

import static mak.fxcalc.app.config.TestFileConfig.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileConfig.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME

class CrossCurrencyParserSpec extends Specification{
	def crossCurrencyParser = new CrossCurrencyTableParser(CSV_STRIPPING_PATTERN)
	def currencyIndexLookUpParser = new CurrencyIndexLookUpParser(CSV_STRIPPING_PATTERN)
	def "File with 12 lines of valid entries should return list of size 12"(String baseCurrency, String termCurrency, String found){
		given:
			def validator = new UserInputFileValidator(CROSS_CURRENCY_EACH_LINE_PATTERN);
			def validatedInputLines = validator.getValidatedInputLines(VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME)
			def currencyIndexLookUpParsedObject = currencyIndexLookUpParser.parseValidatedLines(validatedInputLines)
			def lookUpMap = currencyIndexLookUpParsedObject.getTableData()
			def crossCurrencyParsedObject = crossCurrencyParser.parseValidatedLines(validatedInputLines)
			def matrix = crossCurrencyParsedObject.getTableData()
			def row = lookUpMap.get(baseCurrency);
			def column = lookUpMap.get(termCurrency);
		expect:
			matrix[row][column] == found
		where:
			baseCurrency|termCurrency	|found
			"AUD"		|"USD"			|"DDD"
			"USD"		|"EUR"			|"INV"
			"DKK"		|"NOK"			|"EUR"
	}
}
