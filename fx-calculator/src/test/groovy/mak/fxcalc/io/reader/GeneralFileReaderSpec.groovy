package mak.fxcalc.io.reader

import java.util.regex.Pattern

import mak.fxcalc.io.reader.IDefaultUserInputReader
import mak.fxcalc.io.reader.UserInputFileReader

import static mak.fxcalc.app.config.TestFileName.EMPTY_TEST_FILE_NAME
import static java.util.Collections.EMPTY_LIST
import static mak.fxcalc.app.config.FileInputReaderConfig.CURRENCY_CONVERSION_RATE_PATTERN
import spock.lang.Specification

class GeneralFileReaderSpec extends Specification{
	def IDefaultUserInputReader userInputFileReader = new UserInputFileReader(CURRENCY_CONVERSION_RATE_PATTERN);
	
	
	def "If file name is null it should return empty list"(){
		when:
			def result = userInputFileReader.getValidatedInputLines(null);
		then:
			result == EMPTY_LIST
	}
	
	def "If file name is empty it should return empty list"(){
		when:
			def result = userInputFileReader.getValidatedInputLines("");
		then:
			result == EMPTY_LIST
	}
	
	def "Usage of empty file should return empty list"(){
		when:
			def result = userInputFileReader.getValidatedInputLines(EMPTY_TEST_FILE_NAME);
		then:
			result == EMPTY_LIST
	}
}
