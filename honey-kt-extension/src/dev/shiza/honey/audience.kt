import dev.shiza.honey.dispatcher.AdventureMessageDispatcher
import dev.shiza.honey.message.Message
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

typealias MessageDispatcher = MessageDispatcher<Audience, Component>

fun AdventureMessageDispatcher.createChat(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        Audience.empty(),
        Audience::sendMessage
    )

fun AdventureMessageDispatcher.createActionBar(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter,
        Message.blank(),
        Audience.empty(),
        Audience::sendActionBar
    )

fun Audience.createChat(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        this,  
        Audience::sendMessage
    )

fun Audience.createActionBar(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        this, 
        Audience::sendActionBar
    )
