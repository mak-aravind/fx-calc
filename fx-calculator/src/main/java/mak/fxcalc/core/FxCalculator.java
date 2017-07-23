package mak.fxcalc.core;

import mak.fxcalc.core.emitter.ConversionRateEmitter;
import mak.fxcalc.core.registry.FxCalculatorRegistry;
import mak.fxcalc.lookup.ILookUp;
import mak.fxcalc.parser.CommandParser;

public class FxCalculator {
	
	private final FxCalculatorRegistry fxCalculatorRegistry;
	private final CommandParser commandParser;
	private final String baseCurrency;
	private final String termCurrency;
	private final ConversionRateEmitter conversionRateEmitter;
	
	public FxCalculator(final FxCalculatorRegistry fxCalculatorRegistry,final CommandParser commandParser) {
		this.fxCalculatorRegistry = fxCalculatorRegistry;
		this.commandParser = commandParser;
		this.conversionRateEmitter = new ConversionRateEmitter(this.fxCalculatorRegistry);
		this.baseCurrency = this.commandParser.getBaseCurrency();
		this.termCurrency = this.commandParser.getTermCurrency();
	}
	
	public String getResult(){
		final StringBuilder result = new StringBuilder();
		if(hasValidCurrencies(baseCurrency,termCurrency)){
			result.append(commandParser.getOutputToDisplay());
			result.append(" ");
			result.append(getConvertedValue());
		}
		else{
			appendUnavilability(result);
		}
		return result.toString();
	}
	
	private Float getConvertedValue() {
		final Float amount = Float.parseFloat(commandParser.getAmount());
		final Float convertedValue = baseCurrency.equals(termCurrency) ? amount : 
							amount * conversionRateEmitter.getConversionRate(baseCurrency, termCurrency);
		return Float.parseFloat(String.format(getDecimalPlaceFormatter(), convertedValue));
	}
	
	private String getDecimalPlaceFormatter() {
		final ILookUp currencyDecimalLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
																  .getCurrencyDecimalLookUp();
		final int decimalPoints = currencyDecimalLookUp.getValue(termCurrency);
		final String formatter = "%."+ decimalPoints+ "f";
		return formatter;
	}
	
	private void appendUnavilability(StringBuilder result) {
		result.append("Unable to find rate for ");
		result.append(baseCurrency);
		result.append("/");
		result.append((termCurrency));
	}
	
	private boolean hasValidCurrencies(String baseCurrency, String termCurrency) {
		final ILookUp currencyIndexLookUp = fxCalculatorRegistry.getFxCalculatorLookUpRegistry()
																.getCurrencyIndexLookUp();
		return currencyIndexLookUp.contains(baseCurrency) && currencyIndexLookUp.contains(termCurrency);
	}
}