package mak.fxcalc.service;

import mak.fxcalc.util.lookup.ILookUp;

public class CurrencyIndexLookUpService{
	
	private final ILookUp currencyIndexLookUp;
	
	public CurrencyIndexLookUpService(FxCalculatorRegistry fxCalculatorRegistry) {
		this.currencyIndexLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
				.getCurrencyIndexLookUp();
	}


	
	public boolean hasValidCurrencies(String baseCurrency, String termCurrency) {
		return currencyIndexLookUp.contains(baseCurrency) && currencyIndexLookUp.contains(termCurrency);
	}
}
