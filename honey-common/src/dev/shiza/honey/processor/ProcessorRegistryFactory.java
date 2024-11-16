package dev.shiza.honey.processor;

import com.google.common.collect.ImmutableList;
import java.util.List;

public final class ProcessorRegistryFactory {

  private ProcessorRegistryFactory() {}

  public static ProcessorRegistry create(final List<Processor> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  public static ProcessorRegistry create() {
    return create(ImmutableList.of());
  }
}
