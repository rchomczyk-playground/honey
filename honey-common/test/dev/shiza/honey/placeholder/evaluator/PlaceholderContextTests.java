package dev.shiza.honey.placeholder.evaluator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PlaceholderContextTests {

  @Test
  void mergeContexts() {
    final PlaceholderContext one =
        PlaceholderContext.create().withValue("hello", "world").withPromisedValue("hello", "world");
    final PlaceholderContext two =
        PlaceholderContext.create().withValue("world", "hello").withPromisedValue("world", "hello");
    final PlaceholderContext mergedContext = one.merge(two);
    assertThat(mergedContext.getValues()).hasSize(2);
    assertThat(mergedContext.getPromisedValues()).hasSize(2);
  }

  @Test
  void mergeContextsWithDuplicates() {
    final PlaceholderContext one = PlaceholderContext.create().withValue("hello", "world");
    final PlaceholderContext two = PlaceholderContext.create().withValue("hello", "world2");
    final PlaceholderContext mergedContext = one.merge(two);
    assertThat(mergedContext.getValues()).hasSize(1);
    assertThat(mergedContext.getPromisedValues()).isEmpty();
  }

  @Test
  void mergeEmptyContexts() {
    final PlaceholderContext one = PlaceholderContext.create();
    final PlaceholderContext two = PlaceholderContext.create();
    final PlaceholderContext mergedContext = one.merge(two);
    assertThat(mergedContext.getValues()).isEmpty();
    assertThat(mergedContext.getPromisedValues()).isEmpty();
  }
}
