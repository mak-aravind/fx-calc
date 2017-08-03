package mak.fxcalc.service;

import mak.fxcalc.cache.FileContentsCache;

public class RegistryServiceProvider {
	private final FxCalculatorRegistry fxCalculatorRegistry;
	
	private final ConversionRateService conversionRateService;
	private final CurrencyIndexLookUpService currencyIndexLookUpService;
	private final CurrencyDecimalPlaceService currencyDecimalPlaceService;

	public RegistryServiceProvider(FileContentsCache fileContentsCache) throws EmptyRegistryException{
		this.fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(fileContentsCache);
		if (isEmptyData()) throw new EmptyRegistryException();
		
		this.conversionRateService = new ConversionRateService(this.fxCalculatorRegistry);
		this.currencyIndexLookUpService = new CurrencyIndexLookUpService(this.fxCalculatorRegistry);
		this.currencyDecimalPlaceService = new CurrencyDecimalPlaceService(this.fxCalculatorRegistry);
	}
	
	public boolean isEmptyData() {
		return this.fxCalculatorRegistry == null || this.fxCalculatorRegistry.isEmptyRegistry();
	}

	public ConversionRateService getConversionRateService() {
		return this.conversionRateService;
	}

	public CurrencyIndexLookUpService getCurrencyIndexLookUpService() {
		return this.currencyIndexLookUpService;
	}

	public CurrencyDecimalPlaceService getCurrencyDecimalPlaceService() {
		return this.currencyDecimalPlaceService;
	}
}
