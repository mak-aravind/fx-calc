package mak.fxcalc.core.registry;

import static mak.fxcalc.app.config.FileConfig.VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME;
import static mak.fxcalc.app.config.FileConfig.VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME;

import java.util.List;

import mak.fxcalc.core.cache.FileContentsCache;
import mak.fxcalc.lookup.CurrencyDecimalLookUp;
import mak.fxcalc.lookup.CurrencyIndexLookUp;
import mak.fxcalc.lookup.ILookUp;

public class FxCalculatorLookUpRegistry {
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
		final List<String> currencyDecimalPlacesList = fileContentsCache
				.getCachedFileContentsAsList(VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME);
		final CurrencyDecimalLookUp currencyDecimalLookUp = CurrencyDecimalLookUp
				.createCurrencyDecimalLookUp(currencyDecimalPlacesList);
		if (null == currencyDecimalLookUp)
			return null;
		
		final List<String> crossCurrencyMatrixList = fileContentsCache
				.getCachedFileContentsAsList(VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME);
		final CurrencyIndexLookUp currencyIndexLookup = CurrencyIndexLookUp
				.createCurrencyIndexLookup(crossCurrencyMatrixList);
		if (null == currencyIndexLookup)
			return null;
		
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = new FxCalculatorLookUpRegistry(
				currencyDecimalLookUp, currencyIndexLookup);
		return fxCalculatorLookUpRegistry;
	}
}