package com.caco3.equinox.core.argument

import kotlinx.html.TagConsumer
import kotlinx.html.body
import kotlinx.html.html
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.SynthesizingMethodParameter
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.jvm.javaMethod

class TagConsumerRenderMethodArgumentResolverTest {
    private fun renderFunction(tagConsumer: TagConsumer<*>) = tagConsumer.html {
    }

    private fun getMethodParameter() =
            SynthesizingMethodParameter(::renderFunction.javaMethod!!, 0)

    private val argumentResolver = TagConsumerRenderMethodArgumentResolver()

    private fun resolve(
            parameter: MethodParameter = getMethodParameter(),
            model: Map<String, *> = emptyMap<String, Any>(),
            request: HttpServletRequest = MockHttpServletRequest(),
            response: HttpServletResponse = MockHttpServletResponse()
    ): Any = argumentResolver.resolveArgument(parameter, model, request, response)

    @Test
    fun `tagConsumer argument is supported`() {
        val methodParameter = getMethodParameter()
        assertTrue(argumentResolver.supportsArgument(methodParameter)) {
            "$argumentResolver should support ${TagConsumer::class} parameter"
        }
    }

    @Test
    fun `tagConsumer argument is resolved`() {
        val argument = resolve()

        assertNotNull(argument)
        assertTrue(argument is TagConsumer<*>) {
            "$argument should be instance of ${TagConsumer::class}"
        }
    }

    @Test
    fun `returned tagConsumer is bound to response`() {
        val response = MockHttpServletResponse()
        val argument = resolve(response = response)

        val tagConsumer = argument as TagConsumer<*>
        tagConsumer.html {
            body {
                +"Hi"
            }
        }
        //language=HTML
        assertEquals("""
            <html>
              <body>Hi</body>
            </html>

        """.trimIndent(), response.contentAsString) {
            "Returned $tagConsumer should be bound to response"
        }
    }
}