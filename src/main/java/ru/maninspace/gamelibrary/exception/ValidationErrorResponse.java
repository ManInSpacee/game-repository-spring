package ru.maninspace.gamelibrary.exception;

import java.util.Map;

public record ValidationErrorResponse(String error, Map<String, String> fields) {
}
