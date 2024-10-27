package dev.shiza.honey.placeholder.evaluator.reflection;

import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.ASYNC_CONTEXT;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.EVALUATOR;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.INVOCATION_TIMEOUT;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.NAME;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.SURNAME;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.SYNC_CONTEXT;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.USER;
import static dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluatorTestUtils.placeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import dev.shiza.honey.placeholder.visitor.PromisingPlaceholderVisitor;
import dev.shiza.honey.placeholder.visitor.PlaceholderVisitorImpl;
import dev.shiza.honey.placeholder.evaluator.EvaluatedPlaceholder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class ReflectivePlaceholderEvaluatorTest {

  @Test
  void syncEvaluationToUnknownValue() {
    assertThatCode(
            () ->
                EVALUATOR.evaluate(
                    SYNC_CONTEXT, PlaceholderVisitorImpl.create(), placeholder("unknown.value")))
        .isInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .hasMessageMatching(
            "Could not evaluate placeholder with key \\{\\{(.*?)}}, because of unknown variable named (\\w+)");
  }

  @Test
  void syncEvaluationToUnknownChild() {
    assertThatCode(
            () ->
                EVALUATOR.evaluate(
                    SYNC_CONTEXT, PlaceholderVisitorImpl.create(), placeholder("user.test")))
        .isInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .hasMessageMatching(
            "Could not get method handle for (\\w+) parent with (\\w+) path, because of unexpected exception.");
  }

  @Test
  void syncEvaluationToDirectValue() {
    assertSyncEvaluationEqualTo("user", USER);
  }

  @Test
  void syncEvaluationToSingleChild() {
    assertSyncEvaluationEqualTo("account.owner.name", NAME);
  }

  @Test
  void syncEvaluationToDoubleChild() {
    assertSyncEvaluationEqualTo("account.owner.surname", SURNAME);
  }

  private void assertSyncEvaluationEqualTo(final String expression, final Object expectedValue) {
    final EvaluatedPlaceholder evaluation =
        EVALUATOR.evaluate(SYNC_CONTEXT, PlaceholderVisitorImpl.create(), placeholder(expression));
    assertThat(evaluation.evaluatedValue()).isEqualTo(expectedValue);
  }

  @Test
  void asyncEvaluationToUnknownValue() {
    assertThatCode(
            () ->
                EVALUATOR.evaluate(
                    ASYNC_CONTEXT, PromisingPlaceholderVisitor.create(), placeholder("unknown.value")))
        .isInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .hasMessageMatching(
            "Could not evaluate placeholder with key \\{\\{(.*?)}}, because of unknown promised variable named (\\w+)");
  }

  @Test
  void asyncEvaluationToUnknownChild() {
    final EvaluatedPlaceholder evaluation =
        EVALUATOR.evaluate(ASYNC_CONTEXT, PromisingPlaceholderVisitor.create(), placeholder("user.test"));
    assertThat((CompletableFuture<?>) evaluation.evaluatedValue())
        .isCompletedExceptionally()
        .failsWithin(INVOCATION_TIMEOUT)
        .withThrowableOfType(ExecutionException.class)
        .withCauseInstanceOf(ReflectivePlaceholderEvaluationException.class)
        .withMessageMatching(
            "dev.shiza.honey.placeholder.evaluator.reflection.ReflectivePlaceholderEvaluationException: Could not get method handle for (\\w+) parent with (\\w+) path, because of unexpected exception.");
  }

  @Test
  void asyncEvaluationToDirectValue() {
    assertAsyncEvaluationEqualTo("user", USER);
  }

  @Test
  void asyncEvaluationToSingleChild() {
    assertAsyncEvaluationEqualTo("account.owner.name", NAME);
  }

  @Test
  void asyncEvaluationToDoubleChild() {
    assertAsyncEvaluationEqualTo("account.owner.surname", SURNAME);
  }

  private void assertAsyncEvaluationEqualTo(final String expression, final Object expectedValue) {
    final EvaluatedPlaceholder evaluation =
        EVALUATOR.evaluate(ASYNC_CONTEXT, PromisingPlaceholderVisitor.create(), placeholder(expression));
    assertThat((CompletableFuture<?>) evaluation.evaluatedValue())
        .isCompletedWithValueMatching(evaluatedValue -> evaluatedValue.equals(expectedValue));
  }

  record User(String name, String surname) {}

  record Account(int id, User owner) {}
}
