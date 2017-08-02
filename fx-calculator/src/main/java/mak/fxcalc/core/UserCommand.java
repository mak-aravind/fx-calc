package mak.fxcalc.core;

import static mak.fxcalc.app.config.CommandInputReaderConfig.INPUT_COMMAND_PATTERN;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

import mak.fxcalc.io.reader.IDefaultUserInputReader;
import mak.fxcalc.io.reader.UserInputCommandReader;

public class UserCommand {
	private final String command;
	private String baseCurrency;
	private String termCurrency;
	private String amount;
	private String outputToDisplay;
	
	final IDefaultUserInputReader userInputCommandReader = new UserInputCommandReader(INPUT_COMMAND_PATTERN);

	public UserCommand(final String command) throws InvalidCommandException, IOException {
		this.command = command;
		List<String> validatedInputLines = userInputCommandReader.getValidatedInputLines(this.command);
		if (validatedInputLines.isEmpty()) 
			throw new InvalidCommandException();
		setKeyAttributes(validatedInputLines.get(0));
	}

	private void setKeyAttributes(final String command){
		final Matcher matcher = INPUT_COMMAND_PATTERN.matcher(command);
		if (matcher.find()) {
			this.baseCurrency = getBaseCurrency(matcher);
			this.termCurrency = getTermCurrency(matcher);
			this.amount = matcher.group(5);
			this.outputToDisplay = getFormattedOutput();
		}
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public String getTermCurrency() {
		return termCurrency;
	}

	public String getAmount() {
		return amount;
	}

	public String getOutputToDisplay() {
		return outputToDisplay;
	}

	private String getFormattedOutput() {
		StringBuilder formattedOutputToDisplay = new StringBuilder();
		formattedOutputToDisplay.append(baseCurrency);
		formattedOutputToDisplay.append(" ");
		formattedOutputToDisplay.append(amount);
		formattedOutputToDisplay.append(" = ");
		formattedOutputToDisplay.append(termCurrency);
		return formattedOutputToDisplay.toString();
	}

	private String getBaseCurrency(final Matcher matcher) {
		return matcher.group(1) + matcher.group(2) + matcher.group(3);
	}

	private String getTermCurrency(final Matcher matcher) {
		return matcher.group(9) + matcher.group(10) + matcher.group(11);
	}
}