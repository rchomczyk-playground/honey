package dev.shiza.honey

import dev.shiza.honey.message.dispatcher.MessageDispatcher
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

typealias TypedMessageDispatcher = MessageDispatcher<Audience, Component>

typealias MessageConfigurer = TypedMessageDispatcher.() -> Unit
