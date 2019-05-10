package com.caco3.equinox.core.method

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.bind.annotation.ModelAttribute
import kotlin.reflect.jvm.javaMethod

class RenderMethodTest {
    fun dummyMethod(
            parameter1: String,
            @ModelAttribute("string") annotatedParameter1: String
    ) {
    }

    class GetParametersTests {

        private val renderMethod = RenderMethod.forKotlinFunction(RenderMethodTest::dummyMethod)

        @Test
        fun `should return 2 parameters`() {
            assertEquals(2, renderMethod.parameters.size)
        }

        @Test
        fun `@AliasFor kicks in when reading annotations`() {
            val annotation = renderMethod.parameters[1].getParameterAnnotation(ModelAttribute::class.java)
            assertEquals("string", annotation!!.name)
        }

        @Test
        fun `returned list is read only`() {
            val mutableList = renderMethod.parameters
            assertThrows<UnsupportedOperationException> {
                mutableList.removeAt(0)
            }
        }
    }

    @Test
    fun `descriptive toString returned`() {
        val javaMethod = ::dummyMethod.javaMethod!!
        val toString = RenderMethod.forKotlinFunction(::dummyMethod).toString()
        assertEquals("RenderMethod(targetMethod=$javaMethod)", toString)
    }

    fun returnFirstParameter(any: Any): Any = any

    @Test
    fun `invoke dummy method`() {
        val renderMethod = RenderMethod.forKotlinFunction(::returnFirstParameter)
        val returnValue = renderMethod.invoke(this, true)
        assertEquals(true, returnValue)
    }

    @Test
    fun `constructor throws IllegalArgumentException if method is null`() {
        assertThrows<IllegalArgumentException> {
            RenderMethod(null)
        }
    }

    @Test
    fun `throws IllegalArgumentException if method is not static and target is null`() {
        assertThrows<IllegalArgumentException> {
            RenderMethod.forKotlinFunction(::returnFirstParameter).invoke(null, 42)
        }
    }
}