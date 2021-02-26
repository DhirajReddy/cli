package com.github.cli.service

import com.github.cli.model.ISubscriber
import com.github.cli.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Singleton
class PubSubService : IPubSubService, CoroutineScope {
    private val messages: Queue<Message> = LinkedList<Message>()
    private val subscribers: ConcurrentHashMap<Int, WeakReference<ISubscriber>> = ConcurrentHashMap()

    override fun addMessageToQueue(payload: Message) {
        messages.add(payload)
        launch {
            subscribers.forEach {
                if(messages.size != 0) {
                    it.value.get()?.onMessageReceived(messages.poll().payload)
                }
            }
        }
    }

    override fun addSubscriber(subscriber: ISubscriber) {
        if (!subscribers.containsKey(subscriber.hashCode())) {
            subscribers[subscriber.hashCode()] = WeakReference<ISubscriber>(subscriber)
        }
    }

//    override fun broadCast() {
//        while (messages.size != 0) {
//            subscribers.forEach {
//                it.value.get()?.onMessageReceived(messages.poll().payload)
//            }
//        }
//    }

    override val coroutineContext: CoroutineContext = EmptyCoroutineContext + Dispatchers.Default
}
