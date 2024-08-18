package dev.shiza.honey.placeholder.resolver;

import static dev.shiza.honey.placeholder.resolver.RegexPlaceholderResolverTestUtils.RESOLVER;
import static dev.shiza.honey.placeholder.resolver.RegexPlaceholderResolverTestUtils.placeholder;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RegexPlaceholderResolverTest {

  @Test
  void resolveWithoutPlaceholders() {
    assertThat(RESOLVER.resolve("Hello world!")).isEmpty();
  }

  @Test
  void resolveWithDirectValuePlaceholder() {
    assertThat(RESOLVER.resolve("Hello {{name}}!")).containsExactly(placeholder("name"));
  }

  @Test
  void resolveWithSingleChildPlaceholder() {
    assertThat(RESOLVER.resolve("Hello {{user.name}}!")).containsExactly(placeholder("user.name"));
  }

  @Test
  void resolveWithDoubleChildPlaceholder() {
    assertThat(RESOLVER.resolve("Hello {{account.owner.name}}!"))
        .containsExactly(placeholder("account.owner.name"));
  }

  @Test
  void resolveWithManyPlaceholders() {
    assertThat(
            RESOLVER.resolve(
                "Hello {{account.owner.name}}, balance of account identified by {{account.id}} is equal to {{account.balance}} in {{currency.name}}"))
        .containsExactlyInAnyOrder(
            placeholder("account.owner.name"),
            placeholder("account.id"),
            placeholder("account.balance"),
            placeholder("currency.name"));
  }
}
