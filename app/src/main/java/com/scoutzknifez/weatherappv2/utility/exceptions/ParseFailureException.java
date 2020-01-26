package com.scoutzknifez.weatherappv2.utility.exceptions;

public class ParseFailureException extends RuntimeException {
    public ParseFailureException(Object failedOn, Class parseTo) {
        super("Failed to parse " + failedOn + " (" + failedOn.getClass().getSimpleName() + ") into type " + parseTo.getSimpleName());
    }
}