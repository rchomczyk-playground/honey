package dev.shiza.honey.adventure.message.dispatcher;

import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher;
import dev.shiza.honey.message.dispatcher.MessageConfigurationException;
import dev.shiza.honey.message.dispatcher.MessageConfigurer;
import dev.shiza.honey.message.dispatcher.MessageDispatcher;
import dev.shiza.honey.message.formatter.MessageFormatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public final class AdventureMessageConfigurer
    implements MessageConfigurer<Audience, String, Component> {

  private final AdventureMessageDelivery delivery;
  private final String template;

  AdventureMessageConfigurer(final AdventureMessageDelivery delivery, final String template) {
    this.delivery = delivery;
    this.template = template;
  }

  AdventureMessageConfigurer(final AdventureMessageDelivery delivery) {
    this(delivery, null);
  }

  @Override
  public MessageConfigurer<Audience, String, Component> delivery(
      final AdventureMessageDelivery delivery) {
    return new AdventureMessageConfigurer(delivery, template);
  }

  @Override
  public AdventureMessageConfigurer template(final String template) {
    return new AdventureMessageConfigurer(delivery, template);
  }

  @Override
  public MessageDispatcher<Audience, Component> dispatcher(
      final MessageFormatter<Component> formatter) {
    if (template == null) {
      throw new MessageConfigurationException(
          "Could not get a message dispatcher without a template.");
    }

    return new MessageBaseDispatcher<>(Audience.empty(), delivery.deliver())
        .template(formatter, template);
  }

  AdventureMessageDelivery delivery() {
    return delivery;
  }

  String template() {
    return template;
  }
}
