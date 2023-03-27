package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<Feedback, ParsedText> {
            if (isEmpty(text, replacement)) {
                return Either.Error(Feedback(
                    "Provided text or variables are empty"))
            }
            val template = Template(text, replacement)
            if (template.anyReplacementVariableIsEmpty()) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Variable name might be empty."))
            } else {
                return Either.Success(ParsedText(template.doReplacement().get()))
            }
        }
        private fun isEmpty(text: String, replacement: Map<String, String>): Boolean {
            return replacement.isEmpty() || text.isEmpty()
        }
    }
}
