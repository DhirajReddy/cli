package com.github.cli.model

data class Message(val payload: String) {
    fun clone() : Message {
        return Message(payload)
    }
}
