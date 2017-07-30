package mak.fxcalc.service;

import java.util.List;

import mak.fxcalc.cache.FileContentsCache;
import mak.fxcalc.util.lookup.CurrencyDecimalLookUp;
import mak.fxcalc.util.lookup.CurrencyIndexLookUp;
import mak.fxcalc.util.lookup.ILookUp;

class FxCalculatorLookUpRegistry {
	private final ILookUp currencyIndexLookUp;
	private final ILookUp currencyDecimalLookUp;
	private FxCalculatorLookUpRegistry(CurrencyDecimalLookUp currencyDecimalLookUp, 
			CurrencyIndexLookUp currencyIndexLookUp) {
		this.currencyDecimalLookUp = currencyDecimalLookUp;
		this.currencyIndexLookUp = currencyIndexLookUp;
	}
	public ILookUp getCurrencyIndexLookUp() {
		return this.currencyIndexLookUp;
	}
	public ILookUp getCurrencyDecimalLookUp() {
		return this.currencyDecimalLookUp;
	}
	
	public static FxCalculatorLookUpRegistry buildFxCalculatorLookUpRegistry(FileContentsCache fileContentsCache) {
		final String currencyDecimalPlacesFileName = fileContentsCache.getFilePatterns()
																	  .getFileConfig()
																	  .getCurrencyDecimalPlacesFileName();
		
		final List<String> currencyDecimalPlacesList = fileContentsCache
				.getCachedFileContentsAsList(currencyDecimalPlacesFileName);
		final CurrencyDecimalLookUp currencyDecimalLookUp = CurrencyDecimalLookUp
				.createCurrencyDecimalLookUp(currencyDecimalPlacesList);
		if (null == currencyDecimalLookUp)
			return null;
		
		final String crossCurrencyMatrixFileName = fileContentsCache.getFilePatterns()
				  											  		.getFileConfig()
				  											  		.getCrossCurrencyMatrixFileName();
		
		final List<String> crossCurrencyMatrixList = fileContentsCache
				.getCachedFileContentsAsList(crossCurrencyMatrixFileName);
		final CurrencyIndexLookUp currencyIndexLookup = CurrencyIndexLookUp
				.createCurrencyIndexLookup(crossCurrencyMatrixList);
		if (null == currencyIndexLookup)
			return null;
		
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = new FxCalculatorLookUpRegistry(
				currencyDecimalLookUp, currencyIndexLookup);
		return fxCalculatorLookUpRegistry;
	}
}