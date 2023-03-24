package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {
    @Test
    fun `TemplateEngine should not replace anything if there is not a variable in the text`() {
        val textWithoutVariables = "This is just a text without anything to replace"
        val replacement = mapOf("variable" to "irrelevant")

        assertEquals(
            Either.Success(ParsedText(textWithoutVariables)),
            TemplateEngine.replace(textWithoutVariables, replacement)
        )
    }

    @Test
    fun `TemplateEngine should do nothing if the variable is not present in the text`() {
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
        val expectedResult = "Some problems were encountered while the replacement. " +
                "Please check if the inputs you provided are correct"

        assertEquals(
            Either.Error(Feedback(expectedResult)),
            TemplateEngine.replace(text, replacementWithEmptyVariable)
        )
    }

    @Test
    fun `TemplateEngine should replace all expressions with the variable present in the text`() {
        val text = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with the same \${variable} to be replaced. "
        val replacement = mapOf("variable" to "variable")
        val expectedResult = "This is a text with a variable to be replaced. " +
                "And this is another text with the same variable to be replaced. "

        assertEquals(
            Either.Success(ParsedText(expectedResult)),
            TemplateEngine.replace(text, replacement)
        )
    }

    @Test
    fun `TemplateEngine should replace only the expressions with the specified variable present in the text`() {
        val text = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with the same \${other-variable} to be replaced. " +
                "And this is another text with the same \${another-variable} to be replaced."
        val replacement = mapOf("variable" to "variable")
        val expectedResult = "This is a text with a variable to be replaced. " +
                "And this is another text with the same \${other-variable} to be replaced. " +
                "And this is another text with the same \${another-variable} to be replaced."
        assertEquals(Either.Success(ParsedText(expectedResult)),
            TemplateEngine.replace(text, replacement))
    }
}
