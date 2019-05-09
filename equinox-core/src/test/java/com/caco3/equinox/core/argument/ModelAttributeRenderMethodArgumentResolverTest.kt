package com.caco3.equinox.core.argument

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.annotation.SynthesizingMethodParameter
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.bind.annotation.ModelAttribute
import kotlin.reflect.jvm.javaMethod

class ModelAttributeRenderMethodArgumentResolverTest {

    private fun renderFunction(
            @ModelAttribute("myAttribute") string: String
    ) {
    }

    private val argumentResolver = ModelAttributeRenderMethodArgumentResolver()

    private fun getMethodParameterForRenderFunction() =
            SynthesizingMethodParameter(::renderFunction.javaMethod!!, 0)

    @Test
    fun `argument with @ModelAttribute is supported`() {
        val methodParameter = getMethodParameterForRenderFunction()

        assertTrue(argumentResolver.supportsArgument(methodParameter),
                "$argumentResolver should support parameters with @ModelAttribute")
    }

    @Test
    fun `argument should be resolved`() {
        val methodParameter = getMethodParameterForRenderFunction()
        val model = mapOf<String, Any>("myAttribute" to "myValue")
        val resolvedArgument = argumentResolver.resolveArgument(methodParameter, model,
                MockHttpServletRequest(), MockHttpServletResponse())

        assertEquals("myValue", resolvedArgument) {
            "$argumentResolver should resolve argument from $model"
        }
    }
}
