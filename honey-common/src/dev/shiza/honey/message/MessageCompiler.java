package dev.shiza.honey.message;

import dev.shiza.honey.placeholder.sanitizer.SanitizedPlaceholder;
import java.util.List;

public interface MessageCompiler<T> {

  T compile(final String sanitizedContent, final List<SanitizedPlaceholder> placeholders);
}
