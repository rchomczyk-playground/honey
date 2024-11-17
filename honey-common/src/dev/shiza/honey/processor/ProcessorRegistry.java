package dev.shiza.honey.processor;

/**
* The {@code ProcessorRegistry} interface provides methods for registering
* a preprocessor and preprocessing content. Implementations of this interface
* are expected to maintain a registry of {@link Processor} instances and apply
* these processors to content.
*
* <p>This interface is typically used to modify or enhance content before
* it is processed in its final form.
*/
public interface ProcessorRegistry {

  /**
  * Registers the given processor as a preprocessor. The registered processor
  * will be applied in the preprocessing stage, and it can modify or enhance
  * the input content.
  *
  * @param processor the {@link Processor} to register as a preprocessor
  * @return the current instance of {@code ProcessorRegistry} to allow method chaining
  * @throws IllegalArgumentException if the processor is null
  */
  ProcessorRegistry preprocessor(final Processor processor);

  /**
  * Executes preprocessing on the provided content using all registered
  * preprocessors. The method processes the content sequentially through each
  * preprocessor and returns the final processed output.
  *
  * @param content the original content to process
  * @return the processed content after applying all registered preprocessors
  * @throws IllegalArgumentException if the content is null
  */
  String preprocess(final String content);
}
