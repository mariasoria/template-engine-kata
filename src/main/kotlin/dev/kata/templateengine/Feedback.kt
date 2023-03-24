package dev.kata.templateengine

class Feedback {
    var errorMessage: String

    constructor(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Feedback

        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        return errorMessage.hashCode()
    }

}