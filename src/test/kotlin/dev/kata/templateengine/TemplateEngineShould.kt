package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {

    @Test
    fun `not replace variables in text when they are not present in the variables and should give feedback`() {
        val originalText = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with \${other-variable} to be replaced. " +
                "And this is another text with \${another-variable} to be replaced."
        val replacement = mapOf(
            "variable" to "value",
            "other-variable" to "other-value"
        )
        val expectedResult = "This is a text with a value to be replaced. " +
                "And this is another text with other-value to be replaced. " +
                "And this is another text with \${another-variable} to be replaced."

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("Variable: \${another-variable} could not be replaced")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `not replace variables in text when there are not replacements to be made and should give feedback`() {
        val originalText = "This is a text without a variable to be replaced"
        val replacement = mapOf("non-existing-variable" to "irrelevant")
        val expectedResult = "This is a text without a variable to be replaced"

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("No replacements were made because there were no variables to be replaced")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `give feedback when a given variable has not been found in the text`() {
        val originalText = "This is a text with a \${variable} to be replaced"
        val replacement = mapOf(
            "variable" to "value",
            "non-existing-variable" to "irrelevant"
        )
        val expectedResult = "This is a text with a value to be replaced"
        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("Variable: \${non-existing-variable} could not be found in the text")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `replace all the specified expressions that are present in the text`() {
        val originalText = "This is a text with a \${variable} to be replaced. " +
                "And this is another text with \${other-variable} to be replaced. " +
                "And this is another text with \${another-variable} to be replaced."
        val replacement = mapOf(
            "variable" to "value",
            "other-variable" to "other-value",
            "another-variable" to "another-value"
        )
        val expectedResult = "This is a text with a value to be replaced. " +
                "And this is another text with other-value to be replaced. " +
                "And this is another text with another-value to be replaced."

        assertEquals(expectedResult, TemplateEngine.replace(originalText, replacement).text());
    }

    @Test
    fun `not replace anything when the text is empty and should give feedback`() {
        val originalText = ""
        val replacement = mapOf(
            "variable" to "value"
        )
        val expectedResult = ""

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("Provided text is empty")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `not replace anything when no replacements have been provided and should give feedback`() {
        val originalText = "This is a text with a \${variable} to be replaced. "
        val replacement = emptyMap<String, String>()
        val expectedResult = "This is a text with a \${variable} to be replaced. "

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("Provided variables map is empty")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `not replace anything when the name of a replacement is empty and should give feedback`() {
        val originalText = "This is a text with a \${variable} to be replaced. "
        val replacement = mapOf("" to "value")
        val expectedResult = "This is a text with a \${variable} to be replaced. "

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("There is at least one variable name being an empty string")
        warning.add("Variable: \${variable} could not be replaced")
        assertEquals(warning, template.warnings())
    }

    @Test
    fun `not replace anything when there are no variables and should give feedback`() {
        val originalText = "This is a text with a \${} to be replaced. "
        val replacement = mapOf("variable" to "value")
        val expectedResult = "This is a text with a \${} to be replaced. "

        val template = TemplateEngine.replace(originalText, replacement)
        assertEquals(expectedResult, template.text())

        var warning = mutableListOf<String>()
        warning.add("Some variables to be replaced in text might not be well-formed")
        warning.add("Variable: \${variable} could not be found in the text")
        warning.add("Variable: \${} could not be replaced")
        assertEquals(warning, template.warnings())
    }

}
