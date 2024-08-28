package dev.shiza.honey.processor;

import java.util.Set;

class ProcessorRegistryImpl implements ProcessorRegistry {

  private final Set<Processor> preprocessors;

  ProcessorRegistryImpl(final Set<Processor> preprocessors) {
    this.preprocessors = preprocessors;
  }

  @Override
  public ProcessorRegistry preprocessor(final Processor processor) {
    preprocessors.add(processor);
    return this;
  }

  @Override
  public String preprocess(final String content) {
    String preprocessedContent = content;
    for (final Processor processor : preprocessors) {
      preprocessedContent = processor.process(content);
    }
    return preprocessedContent;
  }
}
