package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {
    @Test
    fun `TemplateEngine should not replace anything if there is not a variable in the text`() {
        val textWithoutVariables = "This is just a text without anything to replace"
        val replacement = mapOf("variable" to "irrelevant")
        assertEquals(textWithoutVariables, TemplateEngine.replace(textWithoutVariables, replacement))
    }

    @Test
    fun `TemplateEngine should remove the variable from the expression if the variable is not present in the text`() {
        val textWithANonExistingVariable = "This is a text with a \${differentVariable} to be replaced"
        val replacement = mapOf("non-existing-variable" to "irrelevant")
        val expectedResult = "This is a text with a  to be replaced"
        assertEquals(expectedResult, TemplateEngine.replace(textWithANonExistingVariable, replacement))
    }

    @Test
    fun `TemplateEngine should replace all expressions with the variable present in the text`() {
        val text = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with the same \${variable} to be replaced."
        val replacement = mapOf("variable" to "variable")
        val expectedResult = "This is a text with a variable to be replaced. " +
                "And this is another text with the same variable to be replaced."
        assertEquals(expectedResult, TemplateEngine.replace(text, replacement))
    }
}
