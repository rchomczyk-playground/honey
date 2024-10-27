package dev.shiza.honey.placeholder.resolver;

import java.util.Set;

public interface PlaceholderResolver {

  Set<Placeholder> resolve(final String message);
}
