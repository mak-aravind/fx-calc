package mak.fxcalc.app.config;

public class FileConfig {
	private FileConfig(){
		throw new AssertionError();//Big Bang when instantiated from inside class(locally)
	}
	
	public static final String VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME="currencyRates.valid.main.data";

	public static final String VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME="crossCurrencyMatrix.valid.main.csv";

	public static final String VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME="currencyDecimalPlaces.valid.main.data";

}