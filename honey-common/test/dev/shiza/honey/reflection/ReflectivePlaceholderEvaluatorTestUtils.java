package dev.shiza.honey.reflection;

import static java.time.Duration.ofSeconds;

import dev.shiza.honey.placeholder.evaluator.PlaceholderContext;
import dev.shiza.honey.placeholder.evaluator.PlaceholderEvaluator;
import dev.shiza.honey.placeholder.resolver.Placeholder;
import dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTest.Account;
import dev.shiza.honey.reflection.ReflectivePlaceholderEvaluatorTest.User;
import java.time.Duration;

final class ReflectivePlaceholderEvaluatorTestUtils {

  static final Duration INVOCATION_TIMEOUT = ofSeconds(5);

  static final String NAME = "John";
  static final String SURNAME = "Doe";

  static final User USER = new User(NAME, SURNAME);
  static final Account ACCOUNT = new Account(1, USER);

  static final PlaceholderEvaluator EVALUATOR = new ReflectivePlaceholderEvaluator();
  static final PlaceholderContext SYNC_CONTEXT =
      PlaceholderContext.create().withValue("account", ACCOUNT).withValue("user", USER);
  static final PlaceholderContext ASYNC_CONTEXT =
      PlaceholderContext.create()
          .withPromisedValue("account", ACCOUNT)
          .withPromisedValue("user", USER);

  static Placeholder placeholder(final String expression) {
    return new Placeholder("{{" + expression + "}}", expression);
  }
}
