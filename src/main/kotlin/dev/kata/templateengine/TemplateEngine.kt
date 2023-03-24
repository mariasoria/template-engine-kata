package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<ParsedText, Feedback> {
            if (providedInputIsNotValid(text, replacement)) {
                return Either.Error(Feedback(
                    "Some problems were encountered. " +
                                "Please check if the inputs you provided are correct"))
            }
            val text = Text(text, replacement)
            if (text.replacementCanNotBeDone()) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Variable name might be empty or replacement location might be missing."))
            }
            if (text.replacementCanBeDone()) {
                text.doReplacement()
            }
            return Either.Success(ParsedText(text.get()))
        }
        private fun providedInputIsNotValid(text: String, replacement: Map<String, String>): Boolean {
            return replacement.isEmpty() || text.isEmpty()
        }
    }
}
