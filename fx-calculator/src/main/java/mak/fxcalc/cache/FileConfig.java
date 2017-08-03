package mak.fxcalc.cache;

public class FileConfig {
	
	private final String currencyRatesFileName;
	private final String crossCurrencyMatrixFileName;
	private final String currencyDecimalPlacesFileName;
	
	public FileConfig(String currencyRatesFileName,
			 String crossCurrencyMatrixFileName,
			 String currencyDecimalPlacesFileName){
		this.currencyRatesFileName = currencyRatesFileName;
		this.crossCurrencyMatrixFileName = crossCurrencyMatrixFileName;
		this.currencyDecimalPlacesFileName = currencyDecimalPlacesFileName;
	}
	
	public String getCurrencyRatesFileName() {
		return this.currencyRatesFileName;
	}

	public String getCrossCurrencyMatrixFileName() {
		return this.crossCurrencyMatrixFileName;
	}

	public String getCurrencyDecimalPlacesFileName() {
		return this.currencyDecimalPlacesFileName;
	}
}