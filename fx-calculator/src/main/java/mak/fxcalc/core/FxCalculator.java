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
	private UserCommand userCommand;
	
	public FxCalculator(RegistryServiceProvider registryServiceProvider) {
		 this.registryServiceProvider =  registryServiceProvider;
	}
	
	public boolean switchedOn() {
		return null != registryServiceProvider || !registryServiceProvider.isEmptyData();
	}
	
	public String processCommand(final String command) {
		UserCommand userCommand;
		try {
			userCommand = new UserCommand(command);
		} catch (InvalidCommandException e) {
			return CommandInputReaderConfig.INVALID_COMMAND;
		} catch (IOException e) {
			System.out.println("<FX-CALCULATOR>Unexpected IO exception: " + e.getMessage());
			return "Please retry your command";
		}
		setUserCommand(userCommand);
		return getResult();
	}
	
	private void setUserCommand(UserCommand userCommand) {
		this.userCommand = userCommand;
		this.baseCurrency = userCommand.getBaseCurrency();
		this.termCurrency = userCommand.getTermCurrency();
	}

	private String getResult(){
		CurrencyIndexLookUpService currencyIndexLookUpService = registryServiceProvider.getCurrencyIndexLookUpService();
		final StringBuilder result = new StringBuilder();
		if(currencyIndexLookUpService.hasValidCurrencies(baseCurrency,termCurrency))
			appendConvertedValue(result);
		else
			appendUnavailability(result);
		
		return result.toString();
	}

	private void appendConvertedValue(final StringBuilder result) {
		result.append(userCommand.getOutputToDisplay());
		result.append(" ");
		result.append(getConvertedValue());
	}

	private void appendUnavailability(final StringBuilder result) {
		result.append("Unable to find rate for ");
		result.append(baseCurrency);
		result.append("/");
		result.append(termCurrency);
	}
	
	private Float getConvertedValue() {
		final CurrencyDecimalPlaceService currencyDecimalPlaceService = registryServiceProvider.getCurrencyDecimalPlaceService();
		final String decimalPlaceFormatter = currencyDecimalPlaceService.getDecimalPlaceFormatter(termCurrency);
		final Float amount = Float.valueOf(userCommand.getAmount().trim()).floatValue();
		final Float convertedValue = calculateConversion(amount);
		return Float.parseFloat(String.format(decimalPlaceFormatter, convertedValue));
	}

	private float calculateConversion(final Float amount) {
		final ConversionRateService conversionRateService = registryServiceProvider.getConversionRateService();
		return baseCurrency.equals(termCurrency) ? amount : 
							amount * conversionRateService.getConversionRate(baseCurrency, termCurrency);
	}
}