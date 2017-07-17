package mak.fxcalc.app.config;

public class TestFileConfig {
	/*private File(){
	throw new AssertionError();//Big Bang when instantiated from inside class(locally)
}*/
	public TestFileConfig(){}
	public static final String EMPTY_TEST_FILE_NAME="empty.test.data";
	
	public static final String VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME="currencyRates.valid.test.data";
	public static final String INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME="currencyRates.invalid.test.data";
	public static final String VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME="crossCurrencyMatrix.valid.test.csv";
	public static final String INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME="crossCurrencyMatrix.invalid.test.csv";
	public static final String VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME="currencyDecimalPlaces.valid.test.data";
	public static final String INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME="currencyDecimalPlaces.invalid.test.data";
}
