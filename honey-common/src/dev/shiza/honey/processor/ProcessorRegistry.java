package dev.shiza.honey.processor;

public interface ProcessorRegistry {

  ProcessorRegistry processor(final ProcessorPhase phase, final Processor processor);

  String process(final ProcessorPhase phase, final String content);
}
