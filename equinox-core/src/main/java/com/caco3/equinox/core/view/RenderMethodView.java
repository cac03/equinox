package com.caco3.equinox.core.view;

import com.caco3.equinox.core.method.RenderMethod;
import com.caco3.equinox.core.render.RenderMethodAdapter;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * {@link RenderMethodView} is a {@link View} implementation
 * wrapping {@link RenderMethod}
 */
public class RenderMethodView implements View {
  private final RenderMethod renderMethod;
  @Nullable
  private final Object bean;
  private final RenderMethodAdapter renderMethodAdapter;

  public RenderMethodView(RenderMethod renderMethod,
                          @Nullable Object bean,
                          RenderMethodAdapter renderMethodAdapter) {
    this.renderMethod = renderMethod;
    this.bean = bean;
    this.renderMethodAdapter = renderMethodAdapter;
  }

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    renderMethodAdapter.render(renderMethod, bean, model, request, response);
  }
}
