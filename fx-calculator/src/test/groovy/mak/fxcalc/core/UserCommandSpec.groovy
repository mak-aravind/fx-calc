package mak.fxcalc.core

import java.util.List

import groovy.transform.EqualsAndHashCode
import spock.lang.Specification

class UserCommandSpec extends Specification{

	def "Any invalid command should throw InvalidCommandException"(String inputCurrencyConversionCommand){
		when:
			def userCommand = new UserCommand(inputCurrencyConversionCommand);
		then:
			thrown InvalidCommandException
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
	
	def "Check whether base Currency and term currency parsed correctly"(String inputCurrencyConversionCommand, 
			String expectedBaseCurrency, String expectedTermCurrency){
		when:
			def userCommand = new UserCommand(inputCurrencyConversionCommand);
		then:
			userCommand.getBaseCurrency().equals(expectedBaseCurrency)
			userCommand.getTermCurrency().equals(expectedTermCurrency)
		where:
			inputCurrencyConversionCommand	|expectedBaseCurrency	|expectedTermCurrency
			"JPY 100 in USD"				|"JPY"					|"USD"
			"AUD 100 in USD"				|"AUD"					|"USD"
			"NOK 100.00 in EUR"				|"NOK"					|"EUR"
			"AUD 100.00 in DKK"				|"AUD"					|"DKK"			
	}
	
	def "Check whether amount parsed correctly"(String inputCurrencyConversionCommand,
		String expectedAmount){
		when:
			def userCommand = new UserCommand(inputCurrencyConversionCommand);
		then:
			userCommand.getAmount().equals(expectedAmount)
		where:
			inputCurrencyConversionCommand	|expectedAmount	
			"JPY 100 in USD"				|"100"
			"AUD 10 in USD"					|"10"
			"NOK 100.00 in EUR"				|"100.00"
			"AUD 75.00 in DKK"				|"75.00"
	}
	
	def "Check whether partial output without conversion amount well formed"(String inputCurrencyConversionCommand,
		String expectedPartialOutput){
		when:
			def userCommand = new UserCommand(inputCurrencyConversionCommand);
		then:
			userCommand.getOutputToDisplay().equals(expectedPartialOutput)
		where:
			inputCurrencyConversionCommand	|expectedPartialOutput
			"JPY 100 in USD"				|"JPY 100 = USD"
			"NOK 100.00 in EUR"				|"NOK 100.00 = EUR"
	}
}
