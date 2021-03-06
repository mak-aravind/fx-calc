package mak.fxcalc.service

import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.cache.FilePatterns
import mak.fxcalc.service.ConversionRateService
import mak.fxcalc.service.CurrencyIndexLookUpService
import mak.fxcalc.service.FxCalculatorLookUpRegistry
import mak.fxcalc.service.FxCalculatorRegistry
import mak.fxcalc.service.RegistryServiceProvider
import spock.lang.Specification
import static mak.fxcalc.registry.RegistryTestConfig.FILE_CONTENTS_CACHE
class CurrencyIndexLoopUpServiceSpec extends Specification{
	
	def "For Given base and term check whether its supported"(String baseCurrency, String termCurrency,
		boolean supported){
		given:
			def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(FILE_CONTENTS_CACHE);
			def CurrencyIndexLookUpService currencyIndexLookUpService = registryServiceProvider.getCurrencyIndexLookUpService()
			def result = currencyIndexLookUpService.hasValidCurrencies(baseCurrency,termCurrency)
		expect:
			result == supported
		where:
			baseCurrency|termCurrency	|supported
			""			|""				|false
			null		|null			|false
			"AUD"		|"XJP"			|false
			"IND"		|"USD"			|false
			"KRW"		|"FJD"			|false
			"USD"		|"USD"			|true
			"AUD"		|"DKK"			|true
			"JPY"		|"USD"			|true
	}
}

