import dev.shiza.honey.TypedMessageDispatcher
import dev.shiza.honey.adventure.message.dispatcher.AdventureMessageDispatcher
import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher
import net.kyori.adventure.audience.Audience

fun AdventureMessageDispatcher.createChat(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        Audience.empty(),
        Audience::sendMessage
    )

fun AdventureMessageDispatcher.createActionBar(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        Audience.empty(),
        Audience::sendActionBar
    )

fun Audience.createChat(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        this,
        Audience::sendMessage
    )

fun Audience.createActionBar(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        this,
        Audience::sendActionBar
    )
