package dev.shiza.honey.placeholder.sanitizer;

public record SanitizedPlaceholder(String key, String expression, Object evaluatedValue) {}
