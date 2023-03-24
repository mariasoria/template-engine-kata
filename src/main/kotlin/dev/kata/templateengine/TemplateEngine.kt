package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<ParsedText, Feedback> {
            val text = Text(text, replacement)
            if (text.containsVariable()) {
                text.doReplacement()
            }
            if (text.providedInputIsIncorrect()) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Please check if the inputs you provided are correct"))
            }
            return Either.Success(ParsedText(text.get()))
        }
    }
}
