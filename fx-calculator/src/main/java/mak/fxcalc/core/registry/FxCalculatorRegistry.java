package mak.fxcalc.core.registry;

import static mak.fxcalc.app.config.FileConfig.VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME;
import static mak.fxcalc.app.config.FileConfig.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME;

import java.util.List;

import mak.fxcalc.core.cache.FileContentsCache;
import mak.fxcalc.lookup.ILookUp;
import mak.fxcalc.table.ConversionRateFeedTable;
import mak.fxcalc.table.CrossCurrencyTable;

public class FxCalculatorRegistry {
	private final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry;
	private final CrossCurrencyTable crossCurrencyTable;
	private final ConversionRateFeedTable conversionRateFeedTable;
	private final boolean emptyRegistry;
	private FxCalculatorRegistry(FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry,
			CrossCurrencyTable crossCurrencyTable, ConversionRateFeedTable conversionRateFeedTable) {
		this.fxCalculatorLookUpRegistry = fxCalculatorLookUpRegistry;
		this.crossCurrencyTable = crossCurrencyTable;
		this.conversionRateFeedTable = conversionRateFeedTable;
		this.emptyRegistry = getStatus(this.fxCalculatorLookUpRegistry, this.crossCurrencyTable, this.conversionRateFeedTable);
	}

	public FxCalculatorLookUpRegistry getFxCalculatorLookUpRegistry() {
		return fxCalculatorLookUpRegistry;
	}

	public CrossCurrencyTable getCrossCurrencyTable() {
		return crossCurrencyTable;
	}

	public ConversionRateFeedTable getConversionRateFeedTable() {
		return conversionRateFeedTable;
	}

	public boolean isEmptyRegistry() {
		return emptyRegistry;
	}

	public static FxCalculatorRegistry buildFxCalculatorRegistry(FileContentsCache fileContentsCache) {
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = buildLookUpRegistry(fileContentsCache);
		if (null == fxCalculatorLookUpRegistry)
			return null;

		final CrossCurrencyTable crossCurrencyTable = buildCrossCurrencyTable(fileContentsCache,
				fxCalculatorLookUpRegistry);
		final ConversionRateFeedTable conversionRateFeedTable = buildConversionRateFeedTable(fileContentsCache);
		final FxCalculatorRegistry fxCalculatorRegistry = new FxCalculatorRegistry(fxCalculatorLookUpRegistry,
				crossCurrencyTable, conversionRateFeedTable);
		return fxCalculatorRegistry;
	}

	private static ConversionRateFeedTable buildConversionRateFeedTable(FileContentsCache fileContentsCache) {
		final List<String> conversionRateFeedLines = fileContentsCache
				.getCachedFileContentsAsList(VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME);
		final ConversionRateFeedTable conversionRateFeedTable = ConversionRateFeedTable
				.createConversionRateFeedLookUp(conversionRateFeedLines);
		return conversionRateFeedTable;
	}

	private static CrossCurrencyTable buildCrossCurrencyTable(FileContentsCache fileContentsCache,
			FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry) {
		final ILookUp currencyIndexLookUp = fxCalculatorLookUpRegistry.getCurrencyIndexLookUp();
		final List<String> crossCurrencyMatrixList = fileContentsCache
				.getCachedFileContentsAsList(VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME);
		final CrossCurrencyTable crossCurrencyTable = CrossCurrencyTable
				.createCrossCurrencyTable(crossCurrencyMatrixList, currencyIndexLookUp);
		return crossCurrencyTable;
	}

	private static FxCalculatorLookUpRegistry buildLookUpRegistry(FileContentsCache fileContentsCache) {
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = FxCalculatorLookUpRegistry
				.buildFxCalculatorLookUpRegistry(fileContentsCache);
		return fxCalculatorLookUpRegistry;
	}
	
	private boolean getStatus(FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry,
			CrossCurrencyTable crossCurrencyTable, ConversionRateFeedTable conversionRateFeedTable) {
		final boolean status = fxCalculatorLookUpRegistry == null 
						|| crossCurrencyTable == null 
						|| conversionRateFeedTable == null;
		return status;
	}
}