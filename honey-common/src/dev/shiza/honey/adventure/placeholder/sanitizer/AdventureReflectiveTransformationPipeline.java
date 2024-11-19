package dev.shiza.honey.adventure.placeholder.sanitizer;

import java.util.List;
import java.util.function.Function;

final class AdventureReflectiveTransformationPipeline {

	private final List<Function<String, String>> transformations;

	AdventureReflectiveTransformationPipeline(final List<Function<String, String>> transformations) {
		this.transformations = transformations;
	}

	public String apply(final String input) {
		return transformations.stream()
				.reduce(Function.identity(), Function::andThen)
				.apply(input);
	}
}