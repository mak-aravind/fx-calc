package mak.fxcalc.table


import mak.fxcalc.registry.FxCalculatorLookUpRegistry
import mak.fxcalc.registry.FxCalculatorRegistry

import static mak.fxcalc.cache.FileConfig.VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.cache.FileConfig.VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME
import static mak.fxcalc.cache.FileConfig.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME

import mak.fxcalc.lookup.CurrencyIndexLookUp

class CrossCurrencyTableSpec {
	
	def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME,
		VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME,
		VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME)
	def FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = fxCalculatorRegistry.getFxCalculatorLookUpRegistry();
	
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
