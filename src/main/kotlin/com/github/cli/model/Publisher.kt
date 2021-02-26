package com.github.cli.model

import com.github.cli.service.IPubSubService

class Publisher: IPublisher {
    override fun publish(payload: Message, pubsubService: IPubSubService) {
        pubsubService.addMessageToQueue(payload)
    }
}
