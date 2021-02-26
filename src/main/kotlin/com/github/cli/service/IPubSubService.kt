package com.github.cli.service

import com.github.cli.model.ISubscriber
import com.github.cli.model.Message

interface IPubSubService {
   fun addMessageToQueue(payload: Message)
    fun addSubscriber(subscriber: ISubscriber)
//    fun broadCast()
}
