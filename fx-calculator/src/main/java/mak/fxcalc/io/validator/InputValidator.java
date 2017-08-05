package mak.fxcalc.io.validator;



import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputValidator implements IValidator{
	
	private Reader input;
	
	final private Pattern pattern;

	public InputValidator(final Pattern pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public void setReader(final Reader fileReader) {
		this.input = fileReader;
	}
	
	@Override
	public List<String> getValidatedInputLines(){
		final Predicate <String> validLine = line -> isValid(line);
		final String[] linesAsArray = new BufferedReader(this.input).lines()
															   		.toArray(String[]::new);
		
		final Supplier<Stream<String>> streamSupplier = () -> Stream.of(linesAsArray);
		if (streamSupplier.get().allMatch(validLine)){
			final List<String> validatedInputLines= streamSupplier.get()
																  .map(line->line.trim())
															  	  .map(line->line.toUpperCase())
															  	  .collect(Collectors.toList());
			return validatedInputLines;
		}
		return Collections.emptyList();
	}
	
	private boolean isValid(final String line){
        return this.pattern.matcher(line)
        				   .matches();
    }
}