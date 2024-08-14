package dev.shiza.honey.placeholder.evaluator;

import dev.shiza.honey.placeholder.resolver.Placeholder;

public record EvaluatedPlaceholder(Placeholder placeholder, Object evaluatedValue) {}
