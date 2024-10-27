package dev.shiza.honey.processor;

public interface ProcessorRegistry {

  ProcessorRegistry preprocessor(final Processor processor);

  String preprocess(final String content);
}
