package mak.fxcalc.io.reader;



import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputReader implements IReader{
	
	private Reader input;
	
	final private Pattern pattern;

	public InputReader(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public void setReader(Reader fileReader) {
		this.input = fileReader;
	}
	
	public List<String> getValidatedInputLines(){
		try (Stream<String> lineStream = new BufferedReader(input).lines()){
			final String[] linesAsArray = lineStream.toArray(String[]::new);
			final Supplier<Stream<String>> streamSupplier = () -> Stream.of(linesAsArray);
		    final Predicate <String> validLine = line -> isValid(line);
			if (streamSupplier.get().allMatch(validLine)){
				final List<String> validatedInputLines= streamSupplier.get()
																	  .map(line->line.trim())
															  		  .map(line->line.toUpperCase())
															  		  .collect(Collectors.toList());
				return validatedInputLines;
			}else{
				return Collections.emptyList();
			}
		}
	}
	public boolean isValid(final String line){
        return this.pattern
        			.matcher(line)
        			.matches();
    }
}