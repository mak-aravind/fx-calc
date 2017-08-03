package mak.fxcalc.service;

import mak.fxcalc.util.lookup.ILookUp;

public class CurrencyIndexLookUpService{
	
	private final ILookUp currencyIndexLookUp;
	
	public CurrencyIndexLookUpService(FxCalculatorRegistry fxCalculatorRegistry) {
		this.currencyIndexLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
				.getCurrencyIndexLookUp();
	}


	
	public boolean hasValidCurrencies(final String baseCurrency, final String termCurrency) {
		return this.currencyIndexLookUp.contains(baseCurrency) && this.currencyIndexLookUp.contains(termCurrency);
	}
}
