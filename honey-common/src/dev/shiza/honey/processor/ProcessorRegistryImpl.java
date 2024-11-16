package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableList;
import java.util.List;

final class ProcessorRegistryImpl implements ProcessorRegistry {

  private final List<Processor> preprocessors;

  ProcessorRegistryImpl(final List<Processor> preprocessors) {
    this.preprocessors = preprocessors;
  }

  @Override
  public ProcessorRegistry preprocessor(final Processor processor) {
    return new ProcessorRegistryImpl(
        ImmutableList.<Processor>builder().addAll(preprocessors).add(processor).build());
  }

  @Override
  public String preprocess(final String content) {
    return preprocessors.stream()
        .reduce(
            content,
            (processedContent, processor) -> processor.process(processedContent),
            (a, b) -> b);
  }
}
