import dev.shiza.honey.MessageConfigurer
import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher
import dev.shiza.honey.message.dispatcher.TitleMessageDispatcher
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

fun times(fadeIn: Int, stay: Int, fadeOut: Int): Triple<Int, Int, Int> =
    Triple(fadeIn, stay, fadeOut)

fun AdventureMessageDispatcher.createTitle(
    recipient: Audience,
    titleConfig: MessageConfigurer,
    subtitleConfig: MessageConfigurer,
    fadeIn: Int,
    stay: Int,
    fadeOut: Int
): TitleMessageDispatcher<Audience, Component> =
    AdventureMessageDispatcher.createTitle()
        .recipient(recipient)
        .title { base -> base.apply(titleConfig) }
        .subtitle { base -> base.apply(subtitleConfig) }
        .times(fadeIn, stay, fadeOut)

fun Audience.createTitle(
    titleConfig: MessageConfigurer,
    subtitleConfig: MessageConfigurer,
    fadeIn: Int,
    stay: Int,
    fadeOut: Int
): TitleMessageDispatcher<Audience, Component> =
    AdventureMessageDispatcher.createTitle()
        .recipient(this)
        .title { base -> base.apply(titleConfig) }
        .subtitle { base -> base.apply(subtitleConfig) }
        .times(fadeIn, stay, fadeOut)

fun Player.createTitle(
    titleConfig: MessageConfigurer,
    subtitleConfig: MessageConfigurer,
    fadeIn: Int,
    stay: Int,
    fadeOut: Int
): TitleMessageDispatcher<Audience, Component> =
    AdventureMessageDispatcher.createTitle()
        .recipient(this)
        .title { base -> base.apply(titleConfig) }
        .subtitle { base -> base.apply(subtitleConfig) }
        .times(fadeIn, stay, fadeOut)

