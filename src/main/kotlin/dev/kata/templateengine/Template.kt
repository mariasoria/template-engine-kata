package dev.kata.templateengine

class Template(private var templateText: String, private var templateVariables: Map<String, String>) {

    var feedbacks: MutableList<String> = mutableListOf()

    companion object {
        fun createTemplate(text: String, templateVariables: Map<String, String>): Template {
            val template = Template(text, templateVariables)
            val validatedTemplate = validateInputs(text, template, templateVariables)
            return validatedTemplate
        }

        private fun validateInputs(
            text: String,
            template: Template,
            templateVariables: Map<String, String>
        ): Template {
            if (text.isEmpty()) {
                template.addWarning("Provided text is empty")
                return template
            }
            if (templateVariables.isEmpty()) {
                template.addWarning("Provided variables are empty")
                return template
            }
            if (!text.contains("\${")) {
                template.addWarning("No replacements were made because there were no variables to be replaced")
                return template
            }
            if (templateVariables.keys.contains("")) {
                template.addWarning("Variable name is an empty string")
            }
            if (text.contains("\${}")) {
                template.addWarning("Variables to be replaced in text might not be well-formed")
            }
            return template
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
        val template = Template(text, templateVariables)
        template.reviewIfThereIsAnyVariableLeft()
        return template
    }

    private fun reviewIfThereIsAnyVariableLeft() {
        if(this.templateText.contains("\${")) {
            this.addWarning("No replacements were made for some variables")
        }
    }

    private fun addWarning(message: String) {
        this.feedbacks.add(message)
    }

    fun text(): String {
        return templateText
    }

    fun feedback(): MutableList<String> {
        return feedbacks
    }

    fun hasWarnings(): Boolean {
        return feedbacks.isNotEmpty()
    }

}