package dev.shiza.honey.message.compiler;

import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;

/**
* Represents a compiler function that takes a sanitized string content and a list of placeholders to produce a compiled message of type T.
*
* @param <T> the type of the compiled message output
*/
@FunctionalInterface
public interface MessageCompiler<T> {

  /**
  * Compiles a message based on the provided sanitized content and placeholders.
  *
  * @param sanitizedContent the sanitized textual content to compile
  * @param placeholders a list of {@link SanitizedPlaceholder} objects which are to be substituted in the sanitized content
  * @return the compiled message of type T
  */
  T compile(final String sanitizedContent, final List<SanitizedPlaceholder> placeholders);
}
