package dev.shiza.honey.placeholder.evaluator;

import static org.assertj.core.api.Assertions.assertThat;

import dev.shiza.honey.placeholder.PlaceholderContext;
import java.util.concurrent.CompletableFuture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlaceholderContextTests {

  @Test
  void mergeContexts() {
    final PlaceholderContext one =
        PlaceholderContext.create()
            .withValue("hello", "world")
            .withAsynchronousValue("hello", CompletableFuture.completedFuture("world"));
    final PlaceholderContext two =
        PlaceholderContext.create()
            .withValue("world", "hello")
            .withAsynchronousValue("world", CompletableFuture.completedFuture("hello"));
    final PlaceholderContext mergedContext = one.merge(two);
    assertThat(mergedContext.getValues()).hasSize(2);
    assertThat(mergedContext.getAsynchronousValues()).hasSize(2);
  }

  @Test
  void mergeContextsWithDuplicatesShouldThrow() {
    final PlaceholderContext one = PlaceholderContext.create().withValue("hello", "world");
    final PlaceholderContext two = PlaceholderContext.create().withValue("hello", "world2");
    Assertions.assertThatCode(() -> one.merge(two)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void mergeEmptyContexts() {
    final PlaceholderContext one = PlaceholderContext.create();
    final PlaceholderContext two = PlaceholderContext.create();
    final PlaceholderContext mergedContext = one.merge(two);
    assertThat(mergedContext.getValues()).isEmpty();
    assertThat(mergedContext.getAsynchronousValues()).isEmpty();
  }
}
