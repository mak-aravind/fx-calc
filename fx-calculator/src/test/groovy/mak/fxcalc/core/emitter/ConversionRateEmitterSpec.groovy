package mak.fxcalc.core.emitter

import mak.fxcalc.core.cache.FileContentsCache
import mak.fxcalc.core.registry.FxCalculatorLookUpRegistry
import mak.fxcalc.core.registry.FxCalculatorRegistry
import mak.fxcalc.lookup.CurrencyDecimalLookUp
import mak.fxcalc.lookup.CurrencyIndexLookUp
import mak.fxcalc.table.ConversionRateFeedTable
import mak.fxcalc.table.CrossCurrencyTable
import spock.lang.Specification

class ConversionRateEmitterSpec extends Specification{
	final FileContentsCache fileContentsCache = new FileContentsCache();
	def FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(fileContentsCache)
	def FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = fxCalculatorRegistry.getFxCalculatorLookUpRegistry();
	
	def "For Given base and term get the conversion rate as expectedConversionRate"(String baseCurrency, String termCurrency,
		Float expectedConversionRate){
		given:
			def ConversionRateEmitter conversionRateEmitter = new ConversionRateEmitter(fxCalculatorRegistry)
			def conversionRate = conversionRateEmitter.getConversionRate(baseCurrency, termCurrency)
		expect:
			conversionRate == expectedConversionRate
		where:
			baseCurrency|termCurrency	|expectedConversionRate
			"AUD"		|"DKK"			|5.0575
			"AUD"		|"JPY"			|100.4102
			"NOK"		|"USD"			|0.1421
	}
}

