package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * An implementation of the {@link ProcessorRegistry} interface which allows for keeping track and
 * manipulating a list of preprocessors.
 */
final class ProcessorRegistryImpl implements ProcessorRegistry {

  private final List<Processor> preprocessors;

  ProcessorRegistryImpl(final List<Processor> preprocessors) {
    this.preprocessors = preprocessors;
  }

  /**
   * Appends a new preprocessor to the registry and returns a new instance of the registry.
   *
   * @param processor the preprocessor to be added to the registry
   * @return a new instance of {@link ProcessorRegistry} with the added preprocessor
   */
  @Override
  public ProcessorRegistry preprocessor(final Processor processor) {
    return new ProcessorRegistryImpl(
        ImmutableList.<Processor>builder().addAll(preprocessors).add(processor).build());
  }

  /**
   * Processes a string content by applying all the preprocessors registered.
   *
   * @param content the content to be processed
   * @return the processed content after all preprocessors have been applied
   */
  @Override
  public String preprocess(final String content) {
    return preprocessors.stream()
        .reduce(
            content,
            (processedContent, processor) -> processor.process(processedContent),
            (a, b) -> b);
  }
}
