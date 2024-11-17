package dev.shiza.honey.conversion;

/**
 * An exception that indicates an error due to implicit type conversion that is not permissible.
 * This class extends {@link IllegalStateException}, thus indicating that the application is in an
 * inappropriate state for the requested operation.
 */
public final class ImplicitConversionException extends IllegalStateException {

  public ImplicitConversionException(final String message) {
    super(message);
  }
}
