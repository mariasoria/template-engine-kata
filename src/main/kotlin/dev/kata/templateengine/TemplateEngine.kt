package dev.kata.templateengine

class TemplateEngine {
    companion object {
        fun replace(text: String, replacement: Map<String, String>): Either<ParsedText, Feedback> {
            var resultantText = ParsedText(text)
            var textReplaced = text
            val variableName = replacement.keys.first()
            val variableValue = replacement[variableName].toString()
            val expression = "\${$variableName}"
            if (variableName.equals("")) {
                return Either.Error(Feedback("Some problems were encountered while the replacement"))
            }
            if (text.contains(expression)) {
                textReplaced = text.replace(expression, variableValue)
                resultantText.setResultantText(textReplaced)
            }
            // Decide if this replacement is needed
            val regex = Regex(pattern = "\\$\\{[^}]*\\}", options = setOf(RegexOption.IGNORE_CASE))
            textReplaced = textReplaced.replace(regex, "")
            resultantText.setResultantText(textReplaced)
            return Either.Success(resultantText)
        }
    }
}
