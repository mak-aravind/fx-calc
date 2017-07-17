package mak.fxcalc.parser;

import static mak.fxcalc.app.config.CommandInputReaderConfig.INPUT_COMMAND_PATTERN;

import java.util.List;
import java.util.regex.Matcher;

public class CommandParser {
	private final List<String> commandList;
	private String command;
	private String baseCurrency;
	private String termCurrency;
	private String amount;
	private String outputToDisplay;

	public CommandParser(List<String> validatedInputLines) {
		this.commandList = validatedInputLines;
		setKeyAttributes();
	}

	public void setKeyAttributes() {
		this.command = commandList.get(0);
		final Matcher matcher = INPUT_COMMAND_PATTERN.matcher(this.command);
		if (matcher.find()) {
			this.baseCurrency = getBaseCurrency(matcher);
			this.termCurrency = getTermCurrency(matcher);
			this.amount = matcher.group(5);
			this.outputToDisplay = getFormattedOutputToDisplay();
		}
	}

	public String getCommand() {
		return command;
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

	private String getFormattedOutputToDisplay() {
		StringBuilder formattedOutputToDisplay = new StringBuilder();
		formattedOutputToDisplay.append(baseCurrency);
		formattedOutputToDisplay.append(" ");
		formattedOutputToDisplay.append(amount);
		formattedOutputToDisplay.append(" = ");
		formattedOutputToDisplay.append(termCurrency);
		return formattedOutputToDisplay.toString();
	}

	private static String getBaseCurrency(final Matcher matcher) {
		return matcher.group(1) + matcher.group(2) + matcher.group(3);
	}

	private static String getTermCurrency(final Matcher matcher) {
		return matcher.group(9) + matcher.group(10) + matcher.group(11);
	}
}