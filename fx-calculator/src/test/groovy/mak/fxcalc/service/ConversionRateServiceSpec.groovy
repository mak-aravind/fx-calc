package mak.fxcalc.service

import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.cache.FilePatterns
import mak.fxcalc.service.ConversionRateService
import mak.fxcalc.service.FxCalculatorLookUpRegistry
import mak.fxcalc.service.FxCalculatorRegistry
import mak.fxcalc.service.RegistryServiceProvider
import spock.lang.Specification
import static mak.fxcalc.registry.RegistryTestConfig.FILE_CONTENTS_CACHE

class ConversionRateServiceSpec extends Specification{
	def "For Given base and term get the conversion rate as expectedConversionRate"(String baseCurrency, String termCurrency,
		Float expectedConversionRate){
		given:
			def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(FILE_CONTENTS_CACHE);
			def ConversionRateService conversionRateEmitter = registryServiceProvider.getConversionRateService()
			def conversionRate = conversionRateEmitter.getConversionRate(baseCurrency, termCurrency)
		expect:
			conversionRate == expectedConversionRate
		where:
			baseCurrency|termCurrency	|expectedConversionRate
			"AUD"		|"DKK"			|5.0576
			"AUD"		|"JPY"			|100.4102
			"NOK"		|"USD"			|0.1422
	}
}

