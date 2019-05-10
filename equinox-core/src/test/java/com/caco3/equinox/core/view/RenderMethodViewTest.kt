package com.caco3.equinox.core.view

import com.caco3.equinox.core.method.RenderMethod
import com.caco3.equinox.core.render.RenderMethodAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import kotlin.reflect.jvm.javaMethod

@ExtendWith(MockitoExtension::class)
class RenderMethodViewTest {
    open class MyView {
        open fun myRenderMethod() = Unit
    }

    @Mock
    private lateinit var myView: MyView
    @Mock
    private lateinit var renderMethodAdapter: RenderMethodAdapter
    private val renderMethod = RenderMethod.forKotlinFunction(MyView::myRenderMethod)

    private lateinit var view: RenderMethodView

    @BeforeEach
    fun setUp() {
        view = RenderMethodView(renderMethod, myView, renderMethodAdapter)
    }

    @Test
    fun `adapter's render method invoked`() {
        val model = emptyMap<String, Any>()
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        view.render(model, request,
                response)

        then(renderMethodAdapter).should()
                .render(renderMethod, myView, model, request, response)
    }


}