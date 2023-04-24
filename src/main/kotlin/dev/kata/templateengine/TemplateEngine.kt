package dev.kata.templateengine

class TemplateEngine {
    companion object {

        fun replace(templateText: String, templateVariables: Map<String, String>): Template {
            val feedback = validateInputs(templateText, templateVariables)
            if(feedback.hasWarnings()){
                return Template(templateText, templateVariables, feedback)
            }
            val template = Template.createTemplate(templateText, templateVariables)
            return template.replace()
        }

        private fun validateInputs(text: String, templateVariables: Map<String, String>): Warnings {
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
}
