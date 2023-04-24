package dev.kata.templateengine

class Template(
    private var templateText: String,
    private var templateVariables: Map<String, String>,
    private var warnings: Warnings
) {

    companion object {
        fun createTemplate(text: String, templateVariables: Map<String, String>): Template {
            val warnings = this.initWarnings(text, templateVariables)
            return Template(text, templateVariables, warnings)
        }

        private fun initWarnings(text: String, templateVariables: Map<String, String>): Warnings {
            if (text.isEmpty()) {
                return Warnings.initWarnings("Provided text is empty")
            }
            if (templateVariables.isEmpty()) {
                return Warnings.initWarnings("Provided variables map is empty")
            }
            if (!text.contains("\${")) {
                return Warnings.initWarnings("No replacements were made because there were no variables to be replaced")
            }
            return Warnings.initWarnings("")
        }
    }

    fun replace(): Template {
        this.addWarningsBeforeReplacementWhenNecessary(templateText, templateVariables)
        var replacedText = doReplacement()
        val template = Template(replacedText, templateVariables, this.warnings)
        this.addWarningToNonReplacedVariables(template)
        return template
    }

    private fun doReplacement(): String {
        val variableNames = templateVariables.keys
        var text = templateText
        var expression: String
        var variableValue: String
        variableNames.forEach { name ->
            expression = "\${$name}"
            variableValue = templateVariables[name].toString()
            text = text.replace(expression, variableValue)
        }
        return text
    }

    private fun addWarningsBeforeReplacementWhenNecessary(
        templateText: String,
        templateVariables: Map<String, String>
    ) {
        this.addWarningWhenThereAreEmptyVariableNames(templateVariables)
        this.addWarningWhenVariablesAreNotWellFormedInText(templateText)
        this.addWarningWhenThereAreVariablesNotPresentInText(templateText, templateVariables)
    }

    private fun addWarningToNonReplacedVariables(template: Template) {
        val regex = Regex(pattern = "\\$\\{[^}]*\\}", options = setOf(RegexOption.IGNORE_CASE))
        val nonReplacedVariables = regex
            .findAll(template.text())
            .map { variable -> variable.value }.toList()
        for (nonReplacedVariable in nonReplacedVariables) {
            this.warnings.addWarning("Variable: $nonReplacedVariable could not be replaced")
        }
    }

    private fun addWarningWhenThereAreEmptyVariableNames(templateVariables: Map<String, String>) {
        if (templateVariables.keys.contains("")) {
            this.warnings.addWarning("There is at least one variable name being an empty string")
        }
    }

    private fun addWarningWhenVariablesAreNotWellFormedInText(templateText: String) {
        if (templateText.contains("\${}")) {
            this.warnings.addWarning("Some variables to be replaced in text might not be well-formed")
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
                this.warnings.addWarning("Variable: \${$variable} could not be found in the text")
            }
        }
    }

    fun text(): String {
        return templateText
    }

    fun warnings(): MutableList<String> {
        return this.warnings.feedback()
    }

    fun hasWarnings(): Boolean {
        return this.warnings.hasWarnings()
    }
}