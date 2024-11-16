import dev.shiza.honey.dispatcher.AdventureMessageDispatcher
import dev.shiza.honey.message.Message
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

typealias MessageFormatter = MessageFormatter<Component>
typealias MessageDispatcher = MessageDispatcher<Audience, Component>

fun AdventureMessageDispatcher.createChat(messageFormatter: MessageFormatter): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        Audience.empty(),
        Audience::sendMessage
    )

fun AdventureMessageDispatcher.createActionBar(messageFormatter: MessageFormatter): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter,
        Message.blank(),
        Audience.empty(),
        Audience::sendActionBar
    )

fun Audience.createChat(messageFormatter: MessageFormatter): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        this,  
        Audience::sendMessage
    )

fun Audience.createActionBar(messageFormatter: MessageFormatter): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        this, 
        Audience::sendActionBar
    )
