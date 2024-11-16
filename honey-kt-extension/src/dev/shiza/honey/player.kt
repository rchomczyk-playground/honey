import dev.shiza.honey.dispatcher.AdventureMessageDispatcher
import dev.shiza.honey.message.Message
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

typealias MessageDispatcher = MessageDispatcher<Audience, Component>

fun Player.createChat(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter, 
        Message.blank(),
        this,  
        Audience::sendMessage
    )

fun Player.createActionBar(): MessageDispatcher =
    MessageBaseDispatcher(
        messageFormatter,  
        Message.blank(),
        this, 
        Audience::sendActionBar
    )
