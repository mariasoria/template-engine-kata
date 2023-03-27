package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {
    @Test
    fun `TemplateEngine should not replace anything if there are not variables to be replaced in the text`() {
        val textWithoutVariables = "This is just a text without anything to replace"
        val replacement = mapOf("variable" to "irrelevant")

        assertEquals(
            Either.Success(ParsedText(textWithoutVariables)),
            TemplateEngine.replace(textWithoutVariables, replacement)
        )
    }

    @Test
    fun `TemplateEngine should not replace anything if the variable is not present in the text`() {
        val textWithANonExistingVariable = "This is a text with a \${differentVariable} to be replaced"
        val replacement = mapOf("non-existing-variable" to "irrelevant")
        val expectedResult = "This is a text with a \${differentVariable} to be replaced"

        assertEquals(
            Either.Success(ParsedText(expectedResult)),
            TemplateEngine.replace(textWithANonExistingVariable, replacement)
        )
    }

    @Test
    fun `TemplateEngine should not replace anything if the variable is empty and give a message error as feedback`() {
        val text = "This is a text with a \${variable} to be replaced"
        val replacementWithEmptyVariable = mapOf("" to "value")
        val expectedFeedbackError = "Some problems were encountered while the replacement. " +
                "Variable name is an empty string."

        assertEquals(
            Either.Error(Feedback(expectedFeedbackError)),
            TemplateEngine.replace(text, replacementWithEmptyVariable)
        )
    }

    @Test
    fun `TemplateEngine should not replace anything if the replacement data is empty and give a message error as feedback`() {
        val text = "This is a text with a \${variable} to be replaced"
        val emptyReplacement = emptyMap<String, String>()
        val expectedFeedbackError = "Provided text or variables are empty"

        assertEquals(
            Either.Error(Feedback(expectedFeedbackError)),
            TemplateEngine.replace(text, emptyReplacement)
        )
    }

    @Test
    fun `TemplateEngine should replace all the specified expressions that are present in the text`() {
        val text = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with \${other-variable} to be replaced. " +
                "And this is another text with \${another-variable} to be replaced."
        val replacement = mapOf(
            "variable" to "variable",
            "other-variable" to "other-variable",
            "another-variable" to "another-variable"
        )
        val expectedResult = "This is a text with a variable to be replaced. " +
                "And this is another text with other-variable to be replaced. " +
                "And this is another text with another-variable to be replaced."

        assertEquals(
            Either.Success(ParsedText(expectedResult)),
            TemplateEngine.replace(text, replacement)
        )
    }
}
