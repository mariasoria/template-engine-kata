package dev.kata.templateengine

class Text {

    private var text: String
    private val variableName: String
    private val variableValue: String
    private val expression: String

    constructor(text: String, replacement: Map<String, String>) {
        this.text = text
        this.variableName = replacement.keys.first()
        this.variableValue = replacement[variableName].toString()
        this.expression = "\${$variableName}"
    }

    fun replacementCanNotBeDone(): Boolean {
        return !this.text.contains(this.expression) &&
                this.variableName == ""
    }

    fun replacementCanBeDone(): Boolean {
        return this.text.contains(expression)
    }

    fun doReplacement() {
        this.text = this.text.replace(expression, variableValue)
    }

    fun get(): String {
        return this.text
    }

}