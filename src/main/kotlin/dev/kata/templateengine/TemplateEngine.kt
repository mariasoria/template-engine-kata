package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<ParsedText, Feedback> {
            val variableName = replacement.keys.first()
            val variableValue = replacement[variableName].toString()
            val expression = "\${$variableName}"
            if (text.contains(expression)) {
                val textReplaced = text.replace(expression, variableValue)
                return Either.Success(ParsedText(textReplaced))
            }
            if (providedInputIsIncorrect(text, expression, variableName)) {
                return Either.Error(Feedback(
                    "Some problems were encountered while the replacement. " +
                                "Please check if the inputs you provided are correct"
                    )
                )
            }
            return Either.Success(ParsedText(text))
        }

        private fun providedInputIsIncorrect(text: String, expression: String, variableName: String) =
            !text.contains(expression) && variableName.equals("")
    }
}
