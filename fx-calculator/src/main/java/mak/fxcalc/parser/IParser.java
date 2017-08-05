package mak.fxcalc.parser;

import java.util.List;

import mak.fxcalc.util.ParsedObject;

public interface IParser<T> {
	ParsedObject<T> parseValidatedLines(final List<String> validatedInputLines);
}