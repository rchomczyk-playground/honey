package dev.shiza.honey.processor;

import java.util.ArrayList;
import java.util.List;

public interface ProcessorRegistry {

  static ProcessorRegistry create() {
    return create(new ArrayList<>());
  }

  static ProcessorRegistry create(final List<Processor> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  ProcessorRegistry preprocessor(final Processor processor);

  String preprocess(final String content);
}
