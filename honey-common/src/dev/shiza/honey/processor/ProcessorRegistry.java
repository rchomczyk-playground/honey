package dev.shiza.honey.processor;

import java.util.HashSet;
import java.util.Set;

public interface ProcessorRegistry {

  static ProcessorRegistry create() {
    return create(new HashSet<>());
  }

  static ProcessorRegistry create(final Set<Processor> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  ProcessorRegistry preprocessor(final Processor processor);

  String preprocess(final String content);
}
