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
	ILookUp getCurrencyIndexLookUp() {
		return this.currencyIndexLookUp;
	}
	ILookUp getCurrencyDecimalLookUp() {
		return this.currencyDecimalLookUp;
	}
	
	static FxCalculatorLookUpRegistry buildFxCalculatorLookUpRegistry(FileContentsCache fileContentsCache) {
		final CurrencyDecimalLookUp currencyDecimalLookUp = buildCurrencyDecimalLookUp(fileContentsCache);
		if (null == currencyDecimalLookUp)
			return null;
		
		final CurrencyIndexLookUp currencyIndexLookup = buildCurrencyIndexLookUp(fileContentsCache);
		if (null == currencyIndexLookup)
			return null;
		
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = new FxCalculatorLookUpRegistry(
				currencyDecimalLookUp, currencyIndexLookup);
		return fxCalculatorLookUpRegistry;
	}
	private static CurrencyIndexLookUp buildCurrencyIndexLookUp(FileContentsCache fileContentsCache) {
		final String crossCurrencyMatrixFileName = fileContentsCache.getFilePatterns()
				  											  		.getFileConfig()
				  											  		.getCrossCurrencyMatrixFileName();
		
		final List<String> crossCurrencyMatrixList = fileContentsCache
				.getCachedFileContentsAsList(crossCurrencyMatrixFileName);
		final CurrencyIndexLookUp currencyIndexLookup = CurrencyIndexLookUp
				.createCurrencyIndexLookup(crossCurrencyMatrixList);
		return currencyIndexLookup;
	}
	private static CurrencyDecimalLookUp buildCurrencyDecimalLookUp(FileContentsCache fileContentsCache) {
		final String currencyDecimalPlacesFileName = fileContentsCache.getFilePatterns()
																	  .getFileConfig()
																	  .getCurrencyDecimalPlacesFileName();
		
		final List<String> currencyDecimalPlacesList = fileContentsCache
				.getCachedFileContentsAsList(currencyDecimalPlacesFileName);
		final CurrencyDecimalLookUp currencyDecimalLookUp = CurrencyDecimalLookUp
				.createCurrencyDecimalLookUp(currencyDecimalPlacesList);
		return currencyDecimalLookUp;
	}
}