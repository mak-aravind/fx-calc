package mak.fxcalc.service;

import mak.fxcalc.util.lookup.ILookUp;

public class CurrencyDecimalPlaceService{
	final ILookUp currencyDecimalLookUp;

	public CurrencyDecimalPlaceService(final FxCalculatorRegistry fxCalculatorRegistry) {
		this.currencyDecimalLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
				  										 .getCurrencyDecimalLookUp();
	}

	public String getDecimalPlaceFormatter(final String termCurrency) {
		final int decimalPoints = this.currencyDecimalLookUp.getValue(termCurrency);
		final String formatter = "%."+ decimalPoints+ "f";
		return formatter;
	}
}
