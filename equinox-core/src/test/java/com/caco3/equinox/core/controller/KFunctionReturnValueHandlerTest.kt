package com.caco3.equinox.core.controller

import com.caco3.equinox.core.method.RenderMethod
import com.caco3.equinox.core.render.RenderMethodAdapter
import com.caco3.equinox.core.view.RenderMethodView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer
import kotlin.reflect.jvm.javaMethod

private fun emptyRenderFunction() = Unit

private fun emptyHandler() = ::emptyRenderFunction

@ExtendWith(MockitoExtension::class)
class KFunctionReturnValueHandlerTest {
    private lateinit var returnValueHandler: HandlerMethodReturnValueHandler
    private val handlerReturnType = MethodParameter
            .forExecutable(::emptyHandler.javaMethod!!, -1)
    @Mock
    private lateinit var renderMethodAdapter: RenderMethodAdapter

    @BeforeEach
    internal fun setUp() {
        returnValueHandler = KFunctionReturnValueHandler(renderMethodAdapter)
    }

    @Test
    fun `function reference is supported`() {
        assertTrue(returnValueHandler.supportsReturnType(handlerReturnType))
    }

    @Test
    fun `view set after return value handling`() {
        val returnValue = ::emptyRenderFunction
        val container = ModelAndViewContainer()
        val webRequest = mock(NativeWebRequest::class.java)

        returnValueHandler.handleReturnValue(returnValue, handlerReturnType, container, webRequest)

        val view = container.view as RenderMethodView
        val expected = RenderMethodView(RenderMethod(returnValue.javaMethod!!), null, renderMethodAdapter)
        assertEquals(expected, view)
    }
}