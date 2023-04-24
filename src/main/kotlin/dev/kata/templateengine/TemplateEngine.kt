package dev.kata.templateengine

class TemplateEngine {
    companion object {

        fun replace(templateText: String, templateVariables: Map<String, String>): Template {
            val warnings = validateInputs(templateText, templateVariables)
            if(warnings.isNotEmpty()){
                return Template.createTemplate(templateText, templateVariables, warnings)
            }
            val template = Template.createTemplate(templateText, templateVariables, warnings)
            return template.replace()
        }

        private fun validateInputs(text: String, templateVariables: Map<String, String>): MutableList<Warning> {
            val warnings = mutableListOf<Warning>()
            if (text.isEmpty()) {
                warnings.add(Warning("Provided text is empty"))
            }
            if (templateVariables.isEmpty()) {
                warnings.add(Warning("Provided variables map is empty"))
            }
            if (!text.contains("\${")) {
                warnings.add(Warning("No replacements were made because there were no variables to be replaced"))
            }
            return warnings
        }
    }
}
