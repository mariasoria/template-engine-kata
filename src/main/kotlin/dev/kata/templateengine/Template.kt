package dev.kata.templateengine

class Template(private var templateText: String, private var templateVariables: Map<String, String>) {

    fun replacementCanNotBeDone(): Boolean {
        val variableName = templateVariables.keys.first()
        val expression = "\${$variableName}"
        return !templateText.contains(expression) &&
                variableName == ""
    }

    fun doReplacement(): Template {
        val variableName = templateVariables.keys.first()
        val variableValue = templateVariables[variableName].toString()
        val expression = "\${$variableName}"

        return Template(templateText.replace(expression, variableValue), templateVariables)
    }

    fun get(): String {
        return templateText
    }

}