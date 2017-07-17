package mak.fxcalc.io.util;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;


public class DefaultFileUtil {
	
	public static Reader getInputStreamReader(final String fileName) throws FileNotFoundException{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader.getResourceAsStream(fileName) == null)
			throw new FileNotFoundException();
		return new InputStreamReader(classLoader.getResourceAsStream(fileName));
	}
}