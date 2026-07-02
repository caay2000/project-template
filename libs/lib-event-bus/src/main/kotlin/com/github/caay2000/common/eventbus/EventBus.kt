package com.github.caay2000.common.eventbus

import mu.KLogger
import mu.KotlinLogging

class EventBus : EventPublisher {
    private val logger: KLogger = KotlinLogging.logger {}

    @PublishedApi
    internal val subscribers: MutableMap<String, List<EventSubscriber<*>>> = mutableMapOf()

    override fun <EVENT : Event> publish(event: EVENT) {
        logger.trace { "publishing event ${event::class.java.simpleName}" }
        subscribersFor(event).forEach { subscriber ->
            logger.trace { "${subscriber::class.java.simpleName} handling event ${event::class.java.simpleName}" }
            subscriber.handle(event)
        }
    }

    inline fun <reified EVENT : Event> subscribe(subscriber: EventSubscriber<EVENT>) {
        val key = EVENT::class.java.canonicalName
        subscribers[key] = subscribers.getOrDefault(key, emptyList()) + subscriber
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribersFor(event: Event): List<EventSubscriber<Event>> = (subscribers[event::class.java.canonicalName] ?: emptyList()) as List<EventSubscriber<Event>>
}
