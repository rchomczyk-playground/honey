package dev.shiza.honey.adventure.message.dispatcher;

import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;

public record AdventureMessageDelivery(String id, BiConsumer<Audience, Component> deliver) {

  static final AdventureMessageDelivery CHAT =
      new AdventureMessageDelivery("chat", Audience::sendMessage);
  static final AdventureMessageDelivery ACTION_BAR =
      new AdventureMessageDelivery("actionbar", Audience::sendActionBar);
  static final AdventureMessageDelivery TIMES =
      new AdventureMessageDelivery("times", (audience, message) -> {});
  static final AdventureMessageDelivery TITLE =
      new AdventureMessageDelivery(
          "title", (audience, message) -> audience.sendTitlePart(TitlePart.TITLE, message));
  static final AdventureMessageDelivery SUBTITLE =
      new AdventureMessageDelivery(
          "subtitle", (audience, message) -> audience.sendTitlePart(TitlePart.SUBTITLE, message));
}
