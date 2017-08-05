package mak.fxcalc.service;


import java.util.List;

import mak.fxcalc.cache.FileContentsCache;
import mak.fxcalc.util.lookup.ILookUp;
import mak.fxcalc.util.table.ConversionRateFeedTable;
import mak.fxcalc.util.table.CrossCurrencyTable;

class FxCalculatorRegistry {
	private final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry;
	private final CrossCurrencyTable crossCurrencyTable;
	private final ConversionRateFeedTable conversionRateFeedTable;
	private final boolean emptyRegistry;
	private FxCalculatorRegistry(final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry,
								 final CrossCurrencyTable crossCurrencyTable, 
								 final ConversionRateFeedTable conversionRateFeedTable) {
		this.fxCalculatorLookUpRegistry = fxCalculatorLookUpRegistry;
		this.crossCurrencyTable = crossCurrencyTable;
		this.conversionRateFeedTable = conversionRateFeedTable;
		this.emptyRegistry = getStatus(this.fxCalculatorLookUpRegistry, this.crossCurrencyTable, this.conversionRateFeedTable);
	}

	FxCalculatorLookUpRegistry getFxCalculatorLookUpRegistry() {
		return this.fxCalculatorLookUpRegistry;
	}

	CrossCurrencyTable getCrossCurrencyTable() {
		return this.crossCurrencyTable;
	}

	ConversionRateFeedTable getConversionRateFeedTable() {
		return this.conversionRateFeedTable;
	}

	boolean isEmptyRegistry() {
		return this.emptyRegistry;
	}

	static FxCalculatorRegistry buildFxCalculatorRegistry(final FileContentsCache fileContentsCache) {
		if (null == fileContentsCache|| fileContentsCache.isEmpty()) return null;
		
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

	private static ConversionRateFeedTable buildConversionRateFeedTable(final FileContentsCache fileContentsCache) {
		
		final String currencyRatesFileName = fileContentsCache.getFilePatterns()
															  .getFileConfig()
															  .getCurrencyRatesFileName();
		
		final List<String> conversionRateFeedLines = fileContentsCache
				.getCachedFileContentsAsList(currencyRatesFileName);
		final ConversionRateFeedTable conversionRateFeedTable = ConversionRateFeedTable
				.createConversionRateFeedLookUp(conversionRateFeedLines);
		return conversionRateFeedTable;
	}

	private static CrossCurrencyTable buildCrossCurrencyTable(final FileContentsCache fileContentsCache,
			FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry) {
		final String crossCurrencyMatrixFileName = fileContentsCache.getFilePatterns()
																	.getFileConfig()
																	.getCrossCurrencyMatrixFileName();
		
		final ILookUp currencyIndexLookUp = fxCalculatorLookUpRegistry.getCurrencyIndexLookUp();
		final List<String> crossCurrencyMatrixList = fileContentsCache
				.getCachedFileContentsAsList(crossCurrencyMatrixFileName);
		final CrossCurrencyTable crossCurrencyTable = CrossCurrencyTable
				.createCrossCurrencyTable(crossCurrencyMatrixList, currencyIndexLookUp);
		return crossCurrencyTable;
	}

	private static FxCalculatorLookUpRegistry buildLookUpRegistry(final FileContentsCache fileContentsCache) {
		final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry = FxCalculatorLookUpRegistry
				.buildFxCalculatorLookUpRegistry(fileContentsCache);
		return fxCalculatorLookUpRegistry;
	}
	
	private boolean getStatus(final FxCalculatorLookUpRegistry fxCalculatorLookUpRegistry,
							  final CrossCurrencyTable crossCurrencyTable, 
							  final ConversionRateFeedTable conversionRateFeedTable) {
		final boolean status = fxCalculatorLookUpRegistry == null 
						|| crossCurrencyTable == null 
						|| conversionRateFeedTable == null;
		return status;
	}
}