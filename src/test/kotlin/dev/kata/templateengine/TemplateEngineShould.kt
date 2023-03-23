package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {
    @Test
    fun `TemplateEngine should`() {
        assertEquals("", TemplateEngine.replace("", mapOf("irrelevant" to "irrelevant")))
    }

}