package dev.kata.templateengine

class Template(private var templateText: String, private var templateVariables: Map<String, String>) {

    fun replacementCanNotBeDone(): Boolean {
        val variableName = templateVariables.keys.first()
        val expression = "\${$variableName}"
        return !templateText.contains(expression) && variableName == ""
    }

    fun doReplacement(): Template {
        val variableNames = templateVariables.keys
        var text = templateText
        var expression: String
        var variableValue: String
        variableNames.forEach {
            expression = "\${$it}"
            variableValue = templateVariables[it].toString()
            text = text.replace(expression, variableValue)
        }
        return Template(text, templateVariables)
    }

    fun get(): String {
        return templateText
    }

}