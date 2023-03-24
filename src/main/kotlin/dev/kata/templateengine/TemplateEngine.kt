package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<ParsedText, Feedback> {
            if (isEmpty(text, replacement)) {
                return Either.Error(Feedback(
                    "Provided text or variables are empty"))
            }
            val template = Template(text, replacement)
            if (template.replacementCanNotBeDone()) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Variable name might be empty or replacement location might be missing."))
            } else {
                return Either.Success(ParsedText(template.doReplacement().get()))
            }
        }
        private fun isEmpty(text: String, replacement: Map<String, String>): Boolean {
            return replacement.isEmpty() || text.isEmpty()
        }
    }
}
