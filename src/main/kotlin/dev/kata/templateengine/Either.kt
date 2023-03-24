package dev.kata.templateengine

sealed class Either<out S, out E> {
    data class Success<out S>(val s: S) : Either<S, Nothing>()
    data class Error<out E>(val e: E) : Either<Nothing, E>()

}