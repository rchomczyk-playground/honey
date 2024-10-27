package dev.shiza.honey.processor;

import java.util.ArrayList;
import java.util.List;

public final class ProcessorRegistryFactory {

  private ProcessorRegistryFactory() {}

  public static ProcessorRegistry create(final List<Processor> preprocessors) {
    return new ProcessorRegistryImpl(preprocessors);
  }

  public static ProcessorRegistry create() {
    return create(new ArrayList<>());
  }
}
