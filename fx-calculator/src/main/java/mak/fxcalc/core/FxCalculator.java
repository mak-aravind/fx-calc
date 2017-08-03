package mak.fxcalc.core;

import java.io.IOException;
import mak.fxcalc.app.config.CommandInputReaderConfig;
import mak.fxcalc.service.ConversionRateService;
import mak.fxcalc.service.CurrencyDecimalPlaceService;
import mak.fxcalc.service.CurrencyIndexLookUpService;
import mak.fxcalc.service.RegistryServiceProvider;

public class FxCalculator {
	
	private final RegistryServiceProvider registryServiceProvider;
	private String baseCurrency;
	private String termCurrency;
	private String amount;
	private UserCommand userCommand;
	
	public FxCalculator(RegistryServiceProvider registryServiceProvider) {
		 this.registryServiceProvider =  registryServiceProvider;
	}
	
	public boolean switchedOn() {
		return (null != this.registryServiceProvider) || (!this.registryServiceProvider.isEmptyData());
	}
	
	public String processCommand(final String command) {
		try {
			final UserCommand userCommandToProcess = new UserCommand(command);
			return getResult(userCommandToProcess);
		} catch (InvalidCommandException e) {
			return CommandInputReaderConfig.INVALID_COMMAND;
		} catch (IOException e) {
			System.out.println("<FX-CALCULATOR>Unexpected IO exception: " + e.getMessage());
			return "Please retry your command";
		}
	}
	
	private String getResult(UserCommand userCommandToProcess) {
		this.userCommand = userCommandToProcess;
		this.baseCurrency = this.userCommand.getBaseCurrency();
		this.termCurrency = this.userCommand.getTermCurrency();
		this.amount = this.userCommand.getAmount();
		return resultServiced();
	}

	private String resultServiced(){
		final CurrencyIndexLookUpService currencyIndexLookUpService = this.registryServiceProvider.getCurrencyIndexLookUpService();
		final StringBuilder result = new StringBuilder();
		if(currencyIndexLookUpService.hasValidCurrencies(this.baseCurrency,this.termCurrency))
			appendConvertedValue(result);
		else
			appendUnavailability(result);
		return result.toString();
	}

	private void appendConvertedValue(final StringBuilder result) {
		result.append(this.userCommand.getOutputToDisplay());
		result.append(" ");
		result.append(getConvertedValue());
	}

	private void appendUnavailability(final StringBuilder result) {
		result.append("Unable to find rate for ");
		result.append(this.baseCurrency);
		result.append("/");
		result.append(this.termCurrency);
	}
	
	private Float getConvertedValue() {
		final CurrencyDecimalPlaceService currencyDecimalPlaceService = this.registryServiceProvider.getCurrencyDecimalPlaceService();
		final String decimalPlaceFormatter = currencyDecimalPlaceService.getDecimalPlaceFormatter(this.termCurrency);
		final Float amount = Float.valueOf(this.amount.trim()).floatValue();
		final Float convertedValue = conversionServiced(amount);
		return Float.parseFloat(String.format(decimalPlaceFormatter, convertedValue));
	}

	private float conversionServiced(final Float amount) {
		final ConversionRateService conversionRateService = this.registryServiceProvider.getConversionRateService();
		return this.baseCurrency.equals(this.termCurrency) ? amount :
				amount * conversionRateService.getConversionRate(this.baseCurrency, this.termCurrency);
	}
}