package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, variables: Map<String, String>): Either<Feedback, ParsedText> {
            if (isEmpty(text, variables)) {
                return Either.Error(Feedback(
                    "Provided text or variables are empty"))
            }
            val template = Template(text, variables)
            if (template.containsEmptyVariable()) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Variable name is an empty string."))
            } else {
                return Either.Success(ParsedText(template.replace().text()))
            }
        }
        private fun isEmpty(text: String, replacement: Map<String, String>): Boolean {
            return replacement.isEmpty() || text.isEmpty()
        }
    }
}
