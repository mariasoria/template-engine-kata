package dev.kata.templateengine

class Template(private var templateText: String, private var templateVariables: Map<String, String>) {

    fun anyReplacementVariableIsEmpty(): Boolean {
        val variableNames = templateVariables.keys
        val nonValidVariable = variableNames.filter { name -> name == "" }
        return nonValidVariable.isNotEmpty()
    }

    fun doReplacement(): Template {
        val variableNames = templateVariables.keys
        var text = templateText
        var expression: String
        var variableValue: String
        variableNames.forEach { name ->
            expression = "\${$name}"
            variableValue = templateVariables[name].toString()
            text = text.replace(expression, variableValue)
        }
        return Template(text, templateVariables)
    }

    fun get(): String {
        return templateText
    }

}