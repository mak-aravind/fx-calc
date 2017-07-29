package mak.fxcalc.registry

import static mak.fxcalc.app.main.FileName.VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.app.main.FileName.VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.main.FileName.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME
import static mak.fxcalc.registry.RegistryTestConfig.FILE_CONTENTS_CACHE

import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.lookup.CurrencyDecimalLookUp
import mak.fxcalc.lookup.CurrencyIndexLookUp
import mak.fxcalc.registry.FxCalculatorLookUpRegistry
import mak.fxcalc.registry.FxCalculatorRegistry
import mak.fxcalc.table.ConversionRateFeedTable
import mak.fxcalc.table.CrossCurrencyTable
import spock.lang.Specification

class FxCalculatorRegistrySpec extends Specification{

	def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(FILE_CONTENTS_CACHE)
	def FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = fxCalculatorRegistry.getFxCalculatorLookUpRegistry();
	
	def "For particular currency the table CurrencyIndexLookUp should return the expected index"(String currency, int expectedIndex){
		given:
			def CurrencyIndexLookUp currencyIndexLookUp = fxCalculatorLookUpRegistry.getCurrencyIndexLookUp();
		expect:
			currencyIndexLookUp.getValue(currency) == expectedIndex
		where:
			currency|expectedIndex
			"AUD"	|1 
			"CAD"	|2 
			"CNY"	|3 
			"CZK"	|4	
			"DKK"	|5
			"EUR"	|6
			"GBP"	|7	
			"JPY"	|8
			"NOK"	|9
			"NZD"	|10
			"USD"	|11
	}
	
	def "For particular currency the table CurrencyDecimalLookUp should return the expected decimal"(String currency, int decimalPlacesExpected){
		given:
			def CurrencyDecimalLookUp currencyDecimalLookUp = fxCalculatorLookUpRegistry.getCurrencyDecimalLookUp();
		expect:
			currencyDecimalLookUp.getValue(currency) == decimalPlacesExpected
		where:
			currency|decimalPlacesExpected
			"AUD"	|	2
			"CAD"	|	2
			"CNY"	|	2
			"CZK"	|	2
			"DKK"	|	2
			"EUR"	|	2
			"GBP"	|	2	
			"JPY"	|	0
			"NOK"	|	2
			"NZD"	|	2
			"USD"	|	2
	}
	
	def "For a Currency combo ConversionRateFeedLookUp should return expectedConversionRate"(String currencyCombo, float expectedConversionRate){
		given:
			def ConversionRateFeedTable conversionRateFeedLookUp = fxCalculatorRegistry.getConversionRateFeedTable();
		expect:
			conversionRateFeedLookUp.getConversionRate(currencyCombo) == expectedConversionRate
		where:
			currencyCombo	|expectedConversionRate
			"AUDUSD"		|0.8371
			"EURNOK"		|8.6651
			"NOKEUR"		|0.1154
			"USDAUD"		|1.1946
	}
	
	def "For a given row and column index it should return expected value"(String baseCurrency, String termCurrency, String found){
		given:
			def CrossCurrencyTable crossCurrencyTable = fxCalculatorRegistry.getCrossCurrencyTable();
			def CurrencyIndexLookUp currencyIndexLookUp = fxCalculatorLookUpRegistry.getCurrencyIndexLookUp();
			def row = currencyIndexLookUp.getValue(baseCurrency);
			def column = currencyIndexLookUp.getValue(termCurrency);
		expect:
			crossCurrencyTable.lookUpCrossViaMatrix(row,column) == found
		where:
			baseCurrency|termCurrency	|found
			"AUD"		|"USD"			|"DDD"
			"USD"		|"EUR"			|"INV"
			"DKK"		|"NOK"			|"EUR"
	}
}

