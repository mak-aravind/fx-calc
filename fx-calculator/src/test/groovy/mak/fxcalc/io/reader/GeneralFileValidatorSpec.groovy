package mak.fxcalc.io.reader

import java.util.regex.Pattern

import mak.fxcalc.io.validator.UserInputFileValidator

import static mak.fxcalc.app.config.TestFileConfig.EMPTY_TEST_FILE_NAME
import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN
import spock.lang.Specification

class GeneralFileValidatorSpec extends Specification{
	def validator = new UserInputFileValidator(CURRENCY_CONVERSION_RATE_PATTERN);
	
	
	def "If file name is null it should return empty list"(){
		when:
			def result = validator.getValidatedInputLines(null);
		then:
			result == EMPTY_LIST
	}
	
	def "If file name is empty it should return empty list"(){
		when:
			def result = validator.getValidatedInputLines("");
		then:
			result == EMPTY_LIST
	}
	
	def "Usage of empty file should return empty list"(){
		when:
			def result = validator.getValidatedInputLines(EMPTY_TEST_FILE_NAME);
		then:
			result == EMPTY_LIST
	}
}
