import dev.shiza.honey.TypedMessageDispatcher
import dev.shiza.honey.message.dispatcher.MessageBaseDispatcher
import net.kyori.adventure.audience.Audience
import org.bukkit.entity.Player

fun Player.createChat(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        this,
        Audience::sendMessage
    )

fun Player.createActionBar(): TypedMessageDispatcher =
    MessageBaseDispatcher(
        this,
        Audience::sendActionBar
    )
