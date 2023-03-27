package dev.kata.templateengine

class Template(private var templateText: String, private var templateVariables: Map<String, String>) {

    fun containsEmptyVariable(): Boolean {
        return templateVariables.keys.contains("")
    }

    fun replace(): Template {
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

    fun text(): String {
        return templateText
    }

}