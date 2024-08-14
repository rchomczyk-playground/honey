package dev.shiza.honey.placeholder.resolver;

import java.util.Set;

public interface PlaceholderResolver {

  static PlaceholderResolver create() {
    return new RegexPlaceholderResolver();
  }

  Set<Placeholder> resolve(final String message);
}
