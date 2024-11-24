package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public final class ProcessorRegistryFactory {

  private ProcessorRegistryFactory() {}

  public static ProcessorRegistry create(final Map<ProcessorPhase, List<Processor>> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  public static ProcessorRegistry create() {
    return create(ImmutableMap.of());
  }
}
