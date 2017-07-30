package mak.fxcalc.service


import mak.fxcalc.service.FxCalculatorLookUpRegistry
import mak.fxcalc.service.FxCalculatorRegistry
import mak.fxcalc.service.RegistryServiceProvider
import mak.fxcalc.util.table.CrossCurrencyTable
import spock.lang.Specification


import static mak.fxcalc.registry.RegistryTestConfig.FILE_CONTENTS_CACHE



class CrossCurrencyTableSpec extends Specification{
	
	def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(FILE_CONTENTS_CACHE);
	def FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = fxCalculatorRegistry.getFxCalculatorLookUpRegistry();
	
	def "For a given row and column index it should return expected value"(String baseCurrency, String termCurrency, String found){
		given:
			def CrossCurrencyTable crossCurrencyTable = fxCalculatorRegistry.getCrossCurrencyTable();
		expect:
			crossCurrencyTable.getIntermediateCurrency(baseCurrency, termCurrency) == found
		where:
			baseCurrency|termCurrency	|found
			"AUD"		|"USD"			|"DDD"
			"USD"		|"EUR"			|"INV"
			"DKK"		|"NOK"			|"EUR"
	}
}
