package mak.fxcalc.service

import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.cache.FilePatterns
import mak.fxcalc.service.ConversionRateService
import mak.fxcalc.service.CurrencyDecimalPlaceService
import mak.fxcalc.service.FxCalculatorLookUpRegistry
import mak.fxcalc.service.FxCalculatorRegistry
import mak.fxcalc.service.RegistryServiceProvider
import spock.lang.Specification
import static mak.fxcalc.registry.RegistryTestConfig.FILE_CONTENTS_CACHE
class CurrencyDecimalPlaceServiceSpec extends Specification{
	def "For particular currency the table CurrencyDecimalLookUp should return the expected decimal"(String currency, Float convertedValue, 
			Float decimalPlaceCorrected){
		given:
			def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(FILE_CONTENTS_CACHE);
			def CurrencyDecimalPlaceService currencyDecimalPlaceService = registryServiceProvider.getCurrencyDecimalPlaceService()
			def decimalPlaceFormatter = currencyDecimalPlaceService.getDecimalPlaceFormatter(currency)
			def result = Float.parseFloat(String.format(decimalPlaceFormatter, convertedValue))
		expect:
			result == decimalPlaceCorrected
		where:
			currency|convertedValue	|decimalPlaceCorrected
			"AUD"	|5.96423254		|5.96
			"JPY"	|5.5426			|6.0
			"JPY"	|5.324568		|5.0
	}
}