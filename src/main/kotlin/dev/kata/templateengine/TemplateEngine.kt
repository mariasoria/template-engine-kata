package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(templateText: String, templateVariables: Map<String, String>): String {
            var template = Template(templateText, templateVariables)
            return template.replace().text()
        }
    }

}
