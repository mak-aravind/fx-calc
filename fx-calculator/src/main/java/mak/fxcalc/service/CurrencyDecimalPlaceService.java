package mak.fxcalc.service;

import mak.fxcalc.util.lookup.ILookUp;

public class CurrencyDecimalPlaceService{
	final ILookUp currencyDecimalLookUp;

	public CurrencyDecimalPlaceService(FxCalculatorRegistry fxCalculatorRegistry) {
		this.currencyDecimalLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
				  										.getCurrencyDecimalLookUp();
	}

	public String getDecimalPlaceFormatter(String termCurrency) {
		final int decimalPoints = currencyDecimalLookUp.getValue(termCurrency);
		final String formatter = "%."+ decimalPoints+ "f";
		return formatter;
	}
}