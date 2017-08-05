package mak.fxcalc.core
import static mak.fxcalc.app.config.TestFileName.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME

import static mak.fxcalc.app.config.TestFileName.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.CommandInputReaderConfig.INVALID_COMMAND

import mak.fxcalc.cache.FileConfig
import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.cache.FilePatterns
import mak.fxcalc.service.RegistryServiceProvider
import mak.fxcalc.service.EmptyRegistryException
import spock.lang.Specification
class FxCalculatorSpec extends Specification{
	
	def FileConfig validFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
		VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
		VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
	def FilePatterns filePatterns = new FilePatterns(validFileConfig)
	def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
	def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(fileContentsCache)
	
	def "File Config with ALL valid file contents should switch on FX-Calculator"(){
		given:
			def FxCalculator fxCalculator = new FxCalculator(registryServiceProvider)
		expect:
			true == fxCalculator.switchedOn()
	}
	
	def "when an invalid command is given to calculator it should return Invalid Command"(String inputCurrencyConversionCommand){
		given:
			def FxCalculator fxCalculator = new FxCalculator(registryServiceProvider)
		expect:
			INVALID_COMMAND.equals(fxCalculator.processCommand(inputCurrencyConversionCommand))
		where:
			inputCurrencyConversionCommand	|_
			"AUD OOO in USD"				|_
			"AUD aa in USD"					|_
			"AUD in USD"					|_
			"UD 100.00 in USD"				|_
			"D 100.00 in USD"				|_
			""								|_
			"AUD 100.00 USD"				|_
			"AUD 100.00 in SD"				|_
			"AUD 100.00 inUSD"				|_
			"AUD 100.00 USD"				|_
	}
	
	def "when a command has currencies not supported then user should get proper message"(String inputCurrencyConversionCommand, String expectedOutput){
		given:
			def FxCalculator fxCalculator = new FxCalculator(registryServiceProvider)
		expect:
			expectedOutput.equals(fxCalculator.processCommand(inputCurrencyConversionCommand))
			
		where:
			inputCurrencyConversionCommand	|expectedOutput
			"AUD 54 in USF"					|"Unable to find rate for AUD/USF"
			"KRW 1000.00 in FJD"			|"Unable to find rate for KRW/FJD"
	}
	
	def "when a command has VALID currencies then user should get output appended with converted value"(String inputCurrencyConversionCommand, 
		String expectedOutput){
		given:
			def FxCalculator fxCalculator = new FxCalculator(registryServiceProvider)
		expect:
			expectedOutput.equals(fxCalculator.processCommand(inputCurrencyConversionCommand))
			
		where:
			inputCurrencyConversionCommand	|expectedOutput
			"AUD 100.00 in USD"				|"AUD 100.00 = USD 83.71"
			"AUD 100.00 in DKK"				|"AUD 100.00 = DKK 505.76"
			"JPY 100 in USD"				|"JPY 100 = USD 0.83"
			"AUD 1 IN JPY"					|"AUD 1 = JPY 100.0"
			"AUD 100.05 in AUD"				|"AUD 100.05 = AUD 100.05"
	}
	
	def "Before processing any command the registry service provider should be checked for empty data"(String inputCurrencyConversionCommand, String expectedOutput){
		given:
			def RegistryServiceProvider registryServiceProvider = Mock()
			def FxCalculator fxCalculator = new FxCalculator(registryServiceProvider)
		and: 'Mock return true'
			1 * registryServiceProvider.isEmptyData() >> true
		when:
			def result = fxCalculator.processCommand(inputCurrencyConversionCommand)
		then:
			expectedOutput.equals(result)
		where:
			inputCurrencyConversionCommand	|expectedOutput
			"AUD 100.05 in AUD"				|"Registry Service Provider has empty data"
	}
}
