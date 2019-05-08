package com.caco3.equinox.core.argument;

import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Strategy interface for resolving render method arguments
 */
public interface RenderMethodArgumentResolver {
  /**
   * Whether this {@link RenderMethodArgumentResolver} supports
   * given argument type resolution
   * <p>
   * For example one implementation might check for presence of
   * a specific annotation
   *
   * @param parameter to check for
   * @return {@code true} if resolution is supported,
   * {@code false} otherwise
   */
  boolean supportsArgument(MethodParameter parameter);

  /**
   * Resolve specified argument described by {@code parameter} argument
   * against given {@code model}, {@code request} and {@code response}.
   * <p>
   * This method is called only if {@link #supportsArgument(MethodParameter)}
   * returned {@code true} for the {@code parameter}
   *
   * @param parameter description of parameter to resolve to
   * @param model     the model prepared for rendering
   * @param request   the current request
   * @param response  the current response
   * @return resolved argument
   * @throws Exception in case of error
   */
  Object resolveArgument(MethodParameter parameter, Map<String, ?> model,
                         HttpServletRequest request, HttpServletResponse response)
      throws Exception;
}
