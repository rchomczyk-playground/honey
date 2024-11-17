package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
* Factory for creating instances of {@link ProcessorRegistry}.
* This class provides static factory methods to instantiate {@link ProcessorRegistry} with or without initial processors.
*/
public final class ProcessorRegistryFactory {

  private ProcessorRegistryFactory() {
  }

  /**
  * Creates a {@link ProcessorRegistry} with the specified list of preprocessors.
  * @param preprocessors the list of processors to include in the registry
  * @return an instance of {@link ProcessorRegistry} initialized with the given preprocessors
  */
  public static ProcessorRegistry create(final List<Processor> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  /**
  * Creates a {@link ProcessorRegistry} with no initial preprocessors.
  * @return an instance of {@link ProcessorRegistry} with no initial processors
  */
  public static ProcessorRegistry create() {
    return create(ImmutableList.of());
  }
}
