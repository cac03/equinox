package com.caco3.equinox.core.render;

import com.caco3.equinox.core.method.RenderMethod;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * {@link RenderMethodAdapter} is an adapter of render method.
 * <p>
 * It creates a level of indirection between view and actual
 * render method to protect view from such details
 */
public interface RenderMethodAdapter {
  /**
   * Invoke render method with supplying all necessary parameters
   * @param renderMethod to invoke
   * @param bean the renderMethod should be invoked on, may be {@code null}
   *             if the method is {@code static}
   * @param model prepared for this view
   * @param request the current request
   * @param response the current response
   */
  void render(RenderMethod renderMethod, @Nullable Object bean,
              Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
