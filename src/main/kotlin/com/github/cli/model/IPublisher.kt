package com.github.cli.model

import com.github.cli.service.IPubSubService

interface IPublisher {
    fun publish(payload: Message, pubsubService: IPubSubService)
}
