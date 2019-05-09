package com.caco3.equinox.core.argument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * {@link ModelAttributeRenderMethodArgumentResolver} is a {@link RenderMethodArgumentResolver}
 * for parameters with {@link ModelAttribute} annotation.
 * <p>
 * It reads {@link ModelAttribute} annotation and uses {@link ModelAttribute#name()}
 * as a key for model map
 */
public class ModelAttributeRenderMethodArgumentResolver implements RenderMethodArgumentResolver {
  private final Log log = LogFactory.getLog(ModelAttributeRenderMethodArgumentResolver.class);

  @Override
  public boolean supportsArgument(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ModelAttribute.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAttribute modelAttribute = parameter.getParameterAnnotation(ModelAttribute.class);
    Assert.notNull(modelAttribute, () -> "parameter=\"" + parameter + "\" has no @ModelAttribute annotation");
    String name = modelAttribute.name();
    if (log.isDebugEnabled()) {
      log.debug("Resolving attribute using annotation=\"" + modelAttribute
          + "\" using model=\"" + model + "\"");
    }
    Object attribute = model.get(name);
    if (log.isDebugEnabled()) {
      log.debug("Resolved attribute=\"" + attribute + "\"");
    }
    return attribute;
  }
}
