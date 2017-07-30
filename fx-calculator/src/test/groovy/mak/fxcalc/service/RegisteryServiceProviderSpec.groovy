package mak.fxcalc.service

import mak.fxcalc.cache.FileConfig
import mak.fxcalc.cache.FileContentsCache
import mak.fxcalc.cache.FilePatterns
import mak.fxcalc.service.EmptyRegistryException
import mak.fxcalc.service.RegistryServiceProvider
import spock.lang.Specification

import static mak.fxcalc.app.config.TestFileName.INVALID_CROSS_CURRENCY_MATRIX_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_RATES_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME

import static mak.fxcalc.app.config.TestFileName.VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME
import static mak.fxcalc.app.config.TestFileName.VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME

class RegisteryServiceProviderSpec extends Specification{

	def "File Config with ANY invalid file contents should throw Service not available exception"(){
		given:
			def FileConfig validFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
															VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
															INVALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)

			def FilePatterns filePatterns = new FilePatterns(validFileConfig)
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
		when:
			def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(fileContentsCache)
		then:
			thrown EmptyRegistryException
	}
	
	def "File Config with ALL valid file contents should not have empty data"(){
		given:
			def FileConfig validFileConfig = new FileConfig(VALID_CURRENCY_RATES_TEST_DATA_FILE_NAME,
																VALID_CROSS_CURRENCY_MATRIX_TEST_DATA_FILE_NAME,
																VALID_CURRENCY_DECIMAL_PLACES_DATA_FILE_NAME)
			
			def FilePatterns filePatterns = new FilePatterns(validFileConfig)
			def FileContentsCache fileContentsCache = new FileContentsCache(filePatterns)
			def RegistryServiceProvider registryServiceProvider = new RegistryServiceProvider(fileContentsCache)
		expect:
			false == registryServiceProvider.isEmptyData()
	}
	
}
