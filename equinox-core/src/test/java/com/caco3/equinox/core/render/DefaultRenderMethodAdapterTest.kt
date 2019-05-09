package com.caco3.equinox.core.render

import com.caco3.equinox.core.argument.RenderMethodArgumentResolver
import com.caco3.equinox.core.method.RenderMethod
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.MethodParameter
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.jvm.javaMethod

class DefaultRenderMethodAdapterTest {

    open class DummyRenderer {
        open fun renderWithMandatoryParameter(
                @ModelAttribute("mandatory") mandatory: String
        ) = Unit
    }

    @Nested
    @ExtendWith(MockitoExtension::class)
    class RenderTests {
        @Mock
        private lateinit var firstArgumentResolver: RenderMethodArgumentResolver
        @Mock
        private lateinit var secondArgumentResolver: RenderMethodArgumentResolver

        private lateinit var renderMethodAdapter: RenderMethodAdapter
        private val mockRequest = MockHttpServletRequest()
        private val mockResponse = MockHttpServletResponse()
        private val renderWithMandatoryParameterMethod =
                RenderMethod(DummyRenderer::renderWithMandatoryParameter.javaMethod!!)
        @Mock
        private lateinit var mockBean: DummyRenderer

        @BeforeEach
        internal fun setUp() {
            renderMethodAdapter = DefaultRenderMethodAdapter.Builder()
                    .addResolver(firstArgumentResolver)
                    .addResolver(secondArgumentResolver)
                    .build()
        }

        private fun callRender(
                renderMethod: RenderMethod = renderWithMandatoryParameterMethod,
                bean: Any? = mockBean,
                model: Map<String, *> = emptyMap<String, Any>(),
                request: HttpServletRequest = mockRequest,
                response: HttpServletResponse = mockResponse
        ) {
            renderMethodAdapter.render(renderMethod, bean, model, request, response)
        }

        @Test
        fun `argument resolvers invoked in order`() {
            given(firstArgumentResolver.supportsArgument(any())).willReturn(false)
            given(secondArgumentResolver.supportsArgument(any())).willReturn(true)

            callRender()

            val inOrder = inOrder(firstArgumentResolver, secondArgumentResolver)
            inOrder.verify(firstArgumentResolver).supportsArgument(any())
            inOrder.verify(secondArgumentResolver).supportsArgument(any())
        }

        @Test
        fun `arguments prepared`() {
            val parameters = renderWithMandatoryParameterMethod.parameters
            given(firstArgumentResolver.supportsArgument(parameters[0]))
                    .willReturn(true)
            val model = mapOf<String, Any>("mandatory" to "mandatoryValue")

            given(firstArgumentResolver.resolveArgument(
                    parameters[0],
                    model,
                    mockRequest,
                    mockResponse
            )).willReturn("mandatoryValue")

            callRender(model = model)

            then(mockBean).should().renderWithMandatoryParameter("mandatoryValue")
        }

        @Test
        fun `IllegalStateException thrown when no resolvers can resolve argument`() {
            val exception = assertThrows<IllegalStateException> {
                callRender()
            }
            assertTrue(exception.message!!.contains("Unable to resolve argument"))
        }
    }

    @Nested
    class BuilderTest {
        @Test
        fun `throws IllegalArgumentException if null provided to addResolver method`() {
            assertThrows<IllegalArgumentException> {
                DefaultRenderMethodAdapter.Builder().addResolver(null)
            }
        }

        @Test
        fun `throws IllegalStateException if resolver is already added`() {
            assertThrows<IllegalStateException> {
                val resolver = object : RenderMethodArgumentResolver {
                    override fun supportsArgument(parameter: MethodParameter?): Boolean =
                            throw NotImplementedError()

                    override fun resolveArgument(
                            parameter: MethodParameter?,
                            model: MutableMap<String, *>?,
                            request: HttpServletRequest?,
                            response: HttpServletResponse?
                    ): Any = throw NotImplementedError()
                }

                DefaultRenderMethodAdapter.Builder()
                        .addResolver(resolver)
                        .addResolver(resolver)
            }
        }
    }
}