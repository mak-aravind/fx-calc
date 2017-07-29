package mak.fxcalc.service;

import mak.fxcalc.registry.FxCalculatorRegistry;
import mak.fxcalc.table.ConversionRateFeedTable;
import mak.fxcalc.table.CrossCurrencyTable;
public class ConversionRateService{

	private final ConversionRateFeedTable conversionRateFeedTable;
	private final CrossCurrencyTable crossCurrencyTable;

	public ConversionRateService(FxCalculatorRegistry fxCalculatorRegistry) {
		this.conversionRateFeedTable = fxCalculatorRegistry.getConversionRateFeedTable();
		this.crossCurrencyTable = fxCalculatorRegistry.getCrossCurrencyTable();
	}

	public Float getConversionRate(final String baseCurrency, final String termCurrency) {
		final String intermediateCurrency = crossCurrencyTable.getIntermediateCurrency(baseCurrency, termCurrency);
		if (intermediateCurrency.equals("DDD") || intermediateCurrency.equals("INV")) {
			return getConversionRateFromFeed(baseCurrency, termCurrency);
		} else {
			final Float crossCurrencyRate1 = getConversionRate(baseCurrency, intermediateCurrency);
			final Float crossCurrrencyRate2 = getConversionRate(intermediateCurrency, termCurrency);
			final Float crossCurrencyRate = crossCurrencyRate1 * crossCurrrencyRate2;
			return (float) (Math.ceil(crossCurrencyRate * 10000.0f) / 10000.0f);
		}
	}
	
	private Float getConversionRateFromFeed(String baseCurrency, String termCurrency) {
		final String currenciesComboKey = baseCurrency + termCurrency;
		return conversionRateFeedTable.getConversionRate(currenciesComboKey);
	}
}