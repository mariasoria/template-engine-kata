package dev.kata.templateengine

class TemplateEngine {
    companion object{
        fun replace(text: String, replacement: Map<String, String>): String {
            val variableName = replacement.keys.first()
            val variableValue = replacement[variableName].toString()
            val regex = Regex(pattern = "\\$\\{[^}]*\\}", options = setOf(RegexOption.IGNORE_CASE))
            if (!text.contains(variableName))
            {
                return text.replace(regex, " ")
            }
            return text.replace(regex, variableValue)
        }
    }
}
