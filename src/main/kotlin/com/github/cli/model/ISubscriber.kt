package com.github.cli.model

interface ISubscriber {
    fun onMessageReceived(payload: String)
}
