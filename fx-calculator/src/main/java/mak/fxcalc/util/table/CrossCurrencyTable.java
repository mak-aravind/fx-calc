package mak.fxcalc.util.table;

import static mak.fxcalc.app.config.ParserConfig.CROSS_CURRENCY_TABLE_PARSER;

import java.util.List;

import mak.fxcalc.parser.IParser;
import mak.fxcalc.parser.ParserFactory;
import mak.fxcalc.util.ParsedObject;
import mak.fxcalc.util.lookup.ILookUp;

public class CrossCurrencyTable{
	private final ILookUp currencyIndexLookUp;
	private final String[][] crossCurrencyMatrix;
	
	private CrossCurrencyTable(String[][] crossCurrencyMatrix,ILookUp currencyIndexLookUp) {
		this.currencyIndexLookUp = currencyIndexLookUp;
		this.crossCurrencyMatrix = crossCurrencyMatrix;
	}
	public static CrossCurrencyTable createCrossCurrencyTable(List<String> validatedInputLines,ILookUp currencyIndexLookUp) {
		@SuppressWarnings("unchecked")
		final IParser<String[][]> crossCurrencyTableParser = ParserFactory.getParser(CROSS_CURRENCY_TABLE_PARSER);
		ParsedObject<String[][]> parsedObject = crossCurrencyTableParser.parseValidatedLines(validatedInputLines);
		if (null == parsedObject) return null;
		String[][] crossCurrencyMatrix = parsedObject.getTableData();
		return new CrossCurrencyTable(crossCurrencyMatrix,currencyIndexLookUp);
	}

	public String getIntermediateCurrency(String baseCurrency, String termCurrency) {
		final int rowIndex = currencyIndexLookUp.getValue(baseCurrency);
		final int columnIndex = currencyIndexLookUp.getValue(termCurrency);
		final String intermediateCurrency = lookUpCrossViaMatrix(rowIndex, columnIndex);
		return intermediateCurrency;
	}
	
	
	private String lookUpCrossViaMatrix(int row, int column){
		return crossCurrencyMatrix[row][column];
	}
}