package dev.kata.templateengine

class Template(
    private var templateText: String,
    private var templateVariables: Map<String, String>,
    private var warnings: MutableList<Warning> = mutableListOf()
) {

    companion object {
        fun createTemplate(text: String, templateVariables: Map<String, String>, warnings: MutableList<Warning>): Template {
            return Template(text, templateVariables, warnings)
        }
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
        return Template(text, templateVariables, warnings)
    }

    fun addWarningsBeforeReplacementWhenNecessary(
        templateText: String,
        templateVariables: Map<String, String>
    ) {
        this.addWarningWhenThereAreEmptyVariableNames(templateVariables)
        this.addWarningWhenVariablesAreNotWellFormedInText(templateText)
        this.addWarningWhenThereAreVariablesNotPresentInText(templateText, templateVariables)
    }

    fun addWarningToNonReplacedVariables(template: Template) {
        val regex = Regex(pattern = "\\$\\{[^}]*\\}", options = setOf(RegexOption.IGNORE_CASE))
        val nonReplacedVariables = regex
            .findAll(template.text())
            .map { variable -> variable.value }.toList()
        for (nonReplacedVariable in nonReplacedVariables) {
            this.warnings.add(Warning("Variable: $nonReplacedVariable could not be replaced"))
        }
    }

    private fun addWarningWhenThereAreEmptyVariableNames(templateVariables: Map<String, String>) {
        if (templateVariables.keys.contains("")) {
            this.warnings.add(Warning("There is at least one variable name being an empty string"))
        }
    }

    private fun addWarningWhenVariablesAreNotWellFormedInText(templateText: String) {
        if (templateText.contains("\${}")) {
            this.warnings.add(Warning("Some variables to be replaced in text might not be well-formed"))
        }
    }

    private fun addWarningWhenThereAreVariablesNotPresentInText(
        templateText: String,
        templateVariables: Map<String, String>
    ) {
        val variableNames = templateVariables.keys
        println(variableNames)
        variableNames.forEach { variable ->
            if (!templateText.contains(variable)) {
            this.warnings.add(Warning("Variable: \${$variable} could not be found in the text"))
            }
        }
    }

    fun text(): String {
        return templateText
    }

    fun warnings(): MutableList<String> {
        return this.warnings.map { warning -> warning.message() }.toMutableList()
    }

}