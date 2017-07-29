package mak.fxcalc.app.main;

import static mak.fxcalc.app.config.CommandInputReaderConfig.EXAMPLE_COMMAND;
import static mak.fxcalc.app.main.FileName.*;

import java.util.Scanner;

import mak.fxcalc.cache.FileConfig;
import mak.fxcalc.cache.FileContentsCache;
import mak.fxcalc.cache.FilePatterns;
import mak.fxcalc.core.FxCalculator;
import mak.fxcalc.service.RegistryServiceProvider;
import mak.fxcalc.service.ServiceNotAvailableException;

public class FxCalculatorUserInteractor {
	private final FxCalculator fxCalculator;
	public FxCalculatorUserInteractor() {
		final FileConfig fileConfig = new FileConfig(VALID_CURRENCY_RATES_MAIN_DATA_FILE_NAME,
													VALID_CROSS_CURRENCY_MAIN_MATRIX_DATA_FILE_NAME,
													VALID_CURRENCY_DECIMAL_MAIN_PLACES_DATA_FILE_NAME);
		final FilePatterns filePatterns = new FilePatterns(fileConfig);
		final FileContentsCache fileContentsCache = new FileContentsCache(filePatterns);
		RegistryServiceProvider registryServiceProvider=null;
		try {
			registryServiceProvider = new RegistryServiceProvider(fileContentsCache);
		} catch (ServiceNotAvailableException e) {
			System.out.println("Cannot process with a mal-formed file/files");
		}
		this.fxCalculator = new FxCalculator(registryServiceProvider);
	}
	
	public static void main(String[] args) {
		final FxCalculatorUserInteractor userInteractor = new FxCalculatorUserInteractor();
		if (userInteractor.fxCalculator.switchOn()){
			userInteractor.printUserInstructions();
			userInteractor.interpretCommands();
		}else{
			System.out.println("Invalid or Corrupted files");
			userInteractor.switchOff();
		}
	}
	private void printUserInstructions() {
		System.out.println("\n\n\t\t\t\t======FX CALCULATOR=====");
		System.out.println("\nEnter commands in format <BASE-CURRENCY> <AMOUNT> IN <TERM-CURRENCY>.");
		printExampleCommand();
		System.out.println("\nCtrl-c to exit\n");
	}

	private void printExampleCommand() {
		System.out.println(EXAMPLE_COMMAND);
	}

	private void interpretCommands() {
		try (final Scanner scanner = new Scanner(System.in)) {
			while (true) {
				final String command = scanner.nextLine();
				final String result = fxCalculator.processCommand(command);
				System.out.println(result + "\n");
			}
		} 
	}
	
	private void switchOff() {
		System.exit(0);
	}
}