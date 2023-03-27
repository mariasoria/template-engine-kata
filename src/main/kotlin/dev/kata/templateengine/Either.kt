package dev.kata.templateengine

sealed class Either<out E, out S> {
    data class Error<out E>(val e: E) : Either<E, Nothing>()
    data class Success<out S>(val s: S) : Either<Nothing, S>()

}