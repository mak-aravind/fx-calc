package mak.fxcalc.io.util

import spock.lang.Specification

import static mak.fxcalc.app.main.FileName.VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME
import static mak.fxcalc.io.util.DefaultFileUtil.getInputStreamReader

class DefaultFileUtilSpec extends Specification{
	
	def "Usage of non-existing file at resource folder should throw FileNotFoundException"(){
		when:
			def fileInput = getInputStreamReader("doesNotExist.txt")
		then:
			thrown(FileNotFoundException)
	}
	
	def "Usage of existing file at resource folder should return non-null Reader"(){
		when:
			def reader = getInputStreamReader(VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME)
		then:
			reader != null;
	}

}
