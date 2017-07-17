package mak.fxcalc.app.main;

import static mak.fxcalc.app.config.CommandInputReaderConfig.INPUT_COMMAND_PATTERN;
import static mak.fxcalc.app.config.CommandInputReaderConfig.INVALID_COMMAND;
import static mak.fxcalc.app.config.CommandInputReaderConfig.EXAMPLE_COMMAND;

import java.util.List;
import java.util.Scanner;

import mak.fxcalc.core.FxCalculator;
import mak.fxcalc.core.cache.FileContentsCache;
import mak.fxcalc.core.registry.FxCalculatorRegistry;
import mak.fxcalc.io.validator.IUserInputValidator;
import mak.fxcalc.io.validator.UserInputCommandValidator;
import mak.fxcalc.parser.CommandParser;

public class FxCalculatorUserInteractor {
	final FileContentsCache fileContentsCache = new FileContentsCache();
	final FxCalculatorRegistry fxCalculatorRegistry = FxCalculatorRegistry.buildFxCalculatorRegistry(fileContentsCache);

	public static void main(String[] args) {
		final FxCalculatorUserInteractor userInteractor = new FxCalculatorUserInteractor();
		userInteractor.switchOn();
		userInteractor.printUserInstructions();
		userInteractor.printExampleCommand();
		userInteractor.interpretCommands();
	}

	private void switchOn() {
		if (isEmptyData())
			switchOff();
	}

	private boolean isEmptyData() {
		if (fileContentsCache == null || fxCalculatorRegistry == null)
			return true;
		else
			return fileContentsCache.isEmpty() || fxCalculatorRegistry.isEmptyRegistry();
	}

	private void printUserInstructions() {
		System.out.println("======FX CALCULATOR=====");
		System.out.println("Enter commands in format <BASE-CURRENCY> <AMOUNT> IN <TERM-CURRENCY>.");
	}

	private void printExampleCommand() {
		System.out.println(EXAMPLE_COMMAND);
	}

	private void interpretCommands() {
		final IUserInputValidator userInputCommandValidator = new UserInputCommandValidator(INPUT_COMMAND_PATTERN);
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				final String command = scanner.nextLine();
				final List<String> validatedCommandInput = userInputCommandValidator.getValidatedInputLines(command);
				if (validatedCommandInput.isEmpty()) {
					System.out.println(INVALID_COMMAND);
					printExampleCommand();
				}else{
					final String result = processCommand(validatedCommandInput);
					System.out.println(result + "\n");
				}
			}
		}
	}

	private String processCommand(final List<String> validatedCommandInput) {
		final CommandParser commandParser = new CommandParser(validatedCommandInput);
		final FxCalculator fxCalculator = new FxCalculator(fxCalculatorRegistry, commandParser);
		final String result = fxCalculator.getResult();
		return result;
	}

	private void switchOff() {
		System.out.println("Invalid or Corrupted files");
		System.exit(0);
	}
}