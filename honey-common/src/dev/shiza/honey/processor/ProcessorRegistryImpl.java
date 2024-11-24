package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class ProcessorRegistryImpl implements ProcessorRegistry {

  private final Map<ProcessorPhase, List<Processor>> preprocessors;

  ProcessorRegistryImpl(final Map<ProcessorPhase, List<Processor>> preprocessors) {
    this.preprocessors = ImmutableMap.copyOf(preprocessors);
  }

  @Override
  public ProcessorRegistry processor(final ProcessorPhase phase, final Processor processor) {
    return new ProcessorRegistryImpl(
        ImmutableMap.<ProcessorPhase, List<Processor>>builder()
            .putAll(preprocessors)
            .put(phase, Collections.singletonList(processor))
            .build());
  }

  @Override
  public String process(final ProcessorPhase phase, final String content) {
    final List<Processor> processors = preprocessors.get(phase);
    if (processors == null || processors.isEmpty()) {
      return content;
    }

    return processors.stream()
        .reduce(
            content,
            (processedContent, processor) -> processor.process(processedContent),
            (a, b) -> b);
  }
}
