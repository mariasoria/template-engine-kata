package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {

    //  text: null, empty string, string ok with variables, string ok without variables
//  dict: null, empty map, map with empty variable, map with variable ok, map with variable ok and empty value

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

        assertEquals(TemplateEngine.replace(text, replacement), expectedResult);
    }
}
