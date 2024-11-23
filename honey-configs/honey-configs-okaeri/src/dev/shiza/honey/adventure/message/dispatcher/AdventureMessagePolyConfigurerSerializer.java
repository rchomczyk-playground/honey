package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageConfigurationException;
import dev.shiza.honey.message.dispatcher.MessageConfigurer;
import dev.shiza.honey.message.dispatcher.MessagePolyConfigurer;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title.Times;
import org.jetbrains.annotations.NotNull;

final class AdventureMessagePolyConfigurerSerializer
    implements ObjectSerializer<MessagePolyConfigurer<Audience, Component>> {

  private final MessageConfigurerSerializer messageConfigurerSerializer;
  private final TitleMessageConfigurerSerializer titleConfigurerSerializer;

  AdventureMessagePolyConfigurerSerializer() {
    this.messageConfigurerSerializer = new MessageConfigurerSerializer();
    this.titleConfigurerSerializer =
        new TitleMessageConfigurerSerializer(messageConfigurerSerializer);
  }

  @Override
  public boolean supports(
      final @NotNull Class<? super MessagePolyConfigurer<Audience, Component>> type) {
    return MessagePolyConfigurer.class.isAssignableFrom(type);
  }

  @Override
  public void serialize(
      final @NotNull MessagePolyConfigurer object,
      final @NotNull SerializationData data,
      final @NotNull GenericsDeclaration generics) {
    if (object instanceof AdventureMessageConfigurer messageConfigurer) {
      messageConfigurerSerializer.serialize(messageConfigurer, data);
    } else if (object instanceof AdventureTitleMessageConfigurer titleConfigurer) {
      titleConfigurerSerializer.serialize(titleConfigurer, data);
    } else {
      throw new MessageConfigurationException("Could not serialize the given message configurer.");
    }
  }

  @Override
  public MessagePolyConfigurer<Audience, Component> deserialize(
      final @NotNull DeserializationData data, final @NotNull GenericsDeclaration generics) {
    final Map<String, Object> dataAsMap = data.asMap();
    return dataAsMap.containsKey(AdventureMessageDelivery.TIMES.id())
        ? titleConfigurerSerializer.deserialize(data)
        : messageConfigurerSerializer.deserialize(dataAsMap);
  }

  private record MessageConfigurerSerializer() {

    private static final Map<String, Supplier<AdventureMessageConfigurer>> FACTORIES =
        Map.of(
            AdventureMessageDelivery.CHAT.id(),
            AdventureMessageConfigurers::createChat,
            AdventureMessageDelivery.ACTION_BAR.id(),
            AdventureMessageConfigurers::createActionBar);

    public void serialize(final AdventureMessageConfigurer object, final SerializationData data) {
      data.add(
          object.delivery().equals(AdventureMessageDelivery.CHAT)
              ? VALUE
              : object.delivery().id(),
          object.template());
    }

    public AdventureMessageConfigurer deserialize(final Map<String, Object> data) {
      if (data.containsKey(VALUE)
          && data.get(VALUE) instanceof String template) {
        return FACTORIES.get(AdventureMessageDelivery.CHAT.id()).get().template(template);
      }

      for (final Entry<String, Object> entry : data.entrySet()) {
        if (entry.getValue() instanceof String template && FACTORIES.containsKey(entry.getKey())) {
          return FACTORIES.get(entry.getKey()).get().template(template);
        }
      }

      throw new MessageConfigurationException(
          "Could not found a message delivery for the given data.");
    }
  }

  private record TitleMessageConfigurerSerializer(
      MessageConfigurerSerializer messageConfigurerSerializer) {

    public void serialize(
        final AdventureTitleMessageConfigurer object, final SerializationData data) {
      data.add(AdventureMessageDelivery.TIMES.id(), object.times(), Times.class);
      serialize(data, object.title(), AdventureMessageDelivery.TITLE);
      serialize(data, object.subtitle(), AdventureMessageDelivery.SUBTITLE);
    }

    public AdventureTitleMessageConfigurer deserialize(final DeserializationData data) {
      return new AdventureTitleMessageConfigurer(
          data.get(AdventureMessageDelivery.TIMES.id(), Times.class),
          deserialize(data, AdventureMessageDelivery.TITLE),
          deserialize(data, AdventureMessageDelivery.SUBTITLE));
    }

    private void serialize(
        final SerializationData data,
        final MessageConfigurer<Audience, String, Component> object,
        final AdventureMessageDelivery delivery) {
      data.add(
          delivery.id(),
          object.delivery(AdventureMessageDelivery.CHAT),
          AdventureMessageConfigurer.class);
    }

    private MessageConfigurer<Audience, String, Component> deserialize(
        final DeserializationData data, final AdventureMessageDelivery delivery) {
      return messageConfigurerSerializer
          .deserialize(
              Map.of(AdventureMessageDelivery.CHAT.id(), data.get(delivery.id(), String.class)))
          .delivery(delivery);
    }
  }
}
