package com.github.caay2000.common.eventbus

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

internal class EventBusTest {
    @Test
    internal fun `subscriber receive the published event`() {
        val stringSubscriber = StringSubscriber()
        val event = StringTestEvent(value = "hi")
        val sut = EventBus()
        sut.subscribe(stringSubscriber)

        sut.publish(event)

        assertThat(stringSubscriber.events).containsExactly(event)
    }

    @Test
    internal fun `multiple subscribers receive the published event`() {
        val stringSubscriber1 = StringSubscriber()
        val stringSubscriber2 = StringSubscriber()
        val event = StringTestEvent(value = "hi")
        val sut = EventBus()
        sut.subscribe(stringSubscriber1)
        sut.subscribe(stringSubscriber2)

        sut.publish(event)

        assertThat(stringSubscriber1.events).containsExactly(event)
        assertThat(stringSubscriber2.events).containsExactly(event)
    }

    @Test
    internal fun `subscribers receive just their requested events`() {
        val stringSubscriber = StringSubscriber()
        val intSubscriber = IntSubscriber()
        val stringEvent = StringTestEvent(value = "hi")
        val intEvent = IntTestEvent(value = 1)
        val sut = EventBus()
        sut.subscribe(stringSubscriber)
        sut.subscribe(intSubscriber)

        sut.publish(stringEvent)
        sut.publish(intEvent)

        assertThat(stringSubscriber.events).containsExactly(stringEvent)
        assertThat(intSubscriber.events).containsExactly(intEvent)
    }

    @Test
    internal fun `an exception thrown by a subscriber propagates to the publisher`() {
        val explodingSubscriber = ExplodingSubscriber()
        val event = StringTestEvent(value = "hi")
        val sut = EventBus()
        sut.subscribe(explodingSubscriber)

        assertThatThrownBy { sut.publish(event) }.isInstanceOf(RuntimeException::class.java).hasMessage("boom")
    }

    inner class StringSubscriber : EventSubscriber<StringTestEvent> {
        val events = mutableListOf<StringTestEvent>()

        override fun handle(event: StringTestEvent) {
            events.add(event)
        }
    }

    inner class IntSubscriber : EventSubscriber<IntTestEvent> {
        val events = mutableListOf<IntTestEvent>()

        override fun handle(event: IntTestEvent) {
            events.add(event)
        }
    }

    inner class ExplodingSubscriber : EventSubscriber<StringTestEvent> {
        override fun handle(event: StringTestEvent) {
            throw RuntimeException("boom")
        }
    }

    internal data class StringTestEvent(
        override val aggregateId: String = UUID.randomUUID().toString(),
        val value: String,
    ) : Event {
        override val eventId: UUID = UUID.randomUUID()
        override val datetime: LocalDateTime = LocalDateTime.now()
    }

    internal data class IntTestEvent(val value: Number) : Event {
        override val aggregateId = UUID.randomUUID().toString()
        override val eventId: UUID = UUID.randomUUID()
        override val datetime: LocalDateTime = LocalDateTime.now()
    }
}
