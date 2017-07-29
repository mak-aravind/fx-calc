package mak.fxcalc.service;

import mak.fxcalc.cache.FileContentsCache;
import mak.fxcalc.registry.FxCalculatorRegistry;

public class RegistryServiceProvider {
	private final FileContentsCache fileContentsCache;
	private final FxCalculatorRegistry fxCalculatorRegistry;
	
	private final ConversionRateService conversionRateService;
	private final CurrencyIndexLookUpService currencyIndexLookUpService;
	private final CurrencyDecimalPlaceService currencyDecimalPlaceService;

	public RegistryServiceProvider(FileContentsCache fileContentsCache) throws ServiceNotAvailableException{
		this.fileContentsCache = fileContentsCache; 
		this.fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(this.fileContentsCache);
		if (null == this.fxCalculatorRegistry) throw new ServiceNotAvailableException();
		this.conversionRateService = new ConversionRateService(this.fxCalculatorRegistry);
		this.currencyIndexLookUpService = new CurrencyIndexLookUpService(this.fxCalculatorRegistry);
		this.currencyDecimalPlaceService = new CurrencyDecimalPlaceService(this.fxCalculatorRegistry);
	}
	
	public boolean isEmptyData() {
		if (this.fileContentsCache == null || this.fxCalculatorRegistry == null)
			return true;
		else
			return this.fileContentsCache.isEmpty() || this.fxCalculatorRegistry.isEmptyRegistry();
	}

	public ConversionRateService getConversionRateService() {
		return this.conversionRateService;
	}

	public CurrencyIndexLookUpService getCurrencyIndexLookUpService() {
		return this.currencyIndexLookUpService;
	}

	public CurrencyDecimalPlaceService getCurrencyDecimalPlaceService() {
		return currencyDecimalPlaceService;
	}
}
