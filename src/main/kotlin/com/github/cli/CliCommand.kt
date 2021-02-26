package com.github.cli

import com.github.cli.model.ISubscriber
import com.github.cli.model.Message
import com.github.cli.model.Publisher
import com.github.cli.service.IPubSubService
import com.github.cli.service.PubSubService
import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import javax.inject.Inject

@Command(name = "cli", description = ["..."],
        mixinStandardHelpOptions = true)
class CliCommand : Runnable {

    @Inject
    lateinit var pubSubService: IPubSubService

    @Option(names = ["-v", "--verbose"], description = ["..."])
    private var verbose : Boolean = false

    override fun run() {
        val publisher = Publisher()
        val message1 = Message("some message 1")

        val subscriber1 = object : ISubscriber {
            val messages = mutableListOf<String>()
            override fun onMessageReceived(payload: String) {
                println(payload)
                messages.add(payload)
            }
        }

        pubSubService.addSubscriber(subscriber1)

        (0 until 50).forEach {
            println("publishing $it")
            publisher.publish(Message("Some message $it"), pubSubService)
        }

        subscriber1.messages.forEach { println(it) }
        Thread.sleep(10000)
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            PicocliRunner.run(CliCommand::class.java, *args)
        }
    }
}
