package dev.shiza.honey.processor;

/**
* Functional interface for processing content.
* <p>
* This interface is designed for operations that take a single {@link String}
* argument and return a processed {@link String} result. It is marked as a
* {@link FunctionalInterface} to ensure that it can be used in contexts where
* lambda expressions and method references are expected, such as with streams
* or in custom functional programming constructs.
* </p>
*/
@FunctionalInterface
public interface Processor {

  /**
  * Processes the specified content and returns the processed result.
  *
  * @param content the content to process
  * @return the processed result
  */
  String process(final String content);
}