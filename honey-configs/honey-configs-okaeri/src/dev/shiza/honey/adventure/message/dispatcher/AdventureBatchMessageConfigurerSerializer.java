package dev.shiza.honey.adventure.message.dispatcher;

import com.google.common.collect.ImmutableList;
import dev.shiza.honey.message.dispatcher.MessagePolyConfigurer;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class AdventureBatchMessageConfigurerSerializer
    implements ObjectSerializer<AdventureBatchMessageConfigurer> {

  AdventureBatchMessageConfigurerSerializer() {}

  @Override
  public boolean supports(final @NotNull Class<? super AdventureBatchMessageConfigurer> type) {
    return AdventureBatchMessageConfigurer.class.isAssignableFrom(type);
  }

  @Override
  public void serialize(
      final @NotNull AdventureBatchMessageConfigurer object,
      final @NotNull SerializationData data,
      final @NotNull GenericsDeclaration generics) {
    if (object.configurers().size() == 1) {
      data.add(VALUE, object.configurers().get(0));
    } else {
      data.addCollection(VALUE, object.configurers(), MessagePolyConfigurer.class);
    }
  }

  @Override
  public AdventureBatchMessageConfigurer deserialize(
      final @NotNull DeserializationData data, final @NotNull GenericsDeclaration generics) {
    final Object rawValue = data.getRaw(VALUE);
    if (rawValue instanceof MessagePolyConfigurer<?, ?>
        || rawValue instanceof Map<?, ?>
        || rawValue instanceof String) {
      return new AdventureBatchMessageConfigurer()
          .add(data.get(VALUE, MessagePolyConfigurer.class));
    }

    return new AdventureBatchMessageConfigurer(
        ImmutableList.copyOf(
            data.getAsCollection(
                VALUE,
                GenericsDeclaration.of(ArrayList.class, List.of(MessagePolyConfigurer.class)))));
  }
}
