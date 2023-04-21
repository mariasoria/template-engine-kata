package dev.kata.templateengine

class TemplateEngine {
    companion object {

        fun replace(templateText: String, templateVariables: Map<String, String>): Template {
            var template = Template.createTemplate(templateText, templateVariables)
            if (template.hasWarnings()) {
                return template
            }
            return template.replace()
        }
    }
}
