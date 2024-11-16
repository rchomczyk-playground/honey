import dev.shiza.honey.dispatcher.TitleMessageDispatcher
import dev.shiza.honey.dispatcher.MessageDispatcher
import dev.shiza.honey.message.Message
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

typealias TitleConfigurer = TitleMessageDispatcher<Audience, Message>.() -> Unit
typealias MessageConfigurer = MessageDispatcher<Audience, Message>.() -> Unit

fun times(fadeIn: Int, stay: Int, fadeOut: Int): Triple<Int, Int, Int> = 
    Triple(fadeIn, stay, fadeOut)


fun TitleMessageDispatcher.createTitle(
    recipient: Audience,
    titleConfig: MessageConfigurer,
    subtitleConfig: MessageConfigurer,
    fadeIn: Int,
    stay: Int,
    fadeOut: Int
): MessageDispatcher<Audience, Message> =
    createTitle()
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
): TitleMessageDispatcher<Audience, Message> =
    createTitle()
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
): TitleMessageDispatcher<Audience, Message> =
    createTitle()
        .recipient(this)
        .title { base -> base.apply(titleConfig) }
        .subtitle { base -> base.apply(subtitleConfig) }
        .times(fadeIn, stay, fadeOut)

