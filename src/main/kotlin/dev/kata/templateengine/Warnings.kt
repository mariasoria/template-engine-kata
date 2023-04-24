package dev.kata.templateengine

class Warnings(private var warnings: MutableList<String>) {

    companion object {
        fun initWarnings(feedback: String): Warnings {
            if(feedback.isNotEmpty()){
                return Warnings(mutableListOf(feedback))
            } else {
                return Warnings(mutableListOf())
            }
        }
    }

    fun feedback(): MutableList<String> {
        return warnings
    }

    fun hasWarnings(): Boolean {
        return warnings.isNotEmpty()
    }

    fun addWarning(message: String) {
        this.warnings.add(message)
    }

}