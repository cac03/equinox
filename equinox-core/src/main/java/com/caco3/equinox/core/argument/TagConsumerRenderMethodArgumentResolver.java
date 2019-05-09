package com.caco3.equinox.core.argument;

import kotlinx.html.consumers.DelayedConsumer;
import kotlinx.html.stream.HTMLStreamBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

/**
 * {@link TagConsumerRenderMethodArgumentResolver} resolves argument of
 * type {@link kotlinx.html.TagConsumer}.
 * <p>
 * Resolved {@link kotlinx.html.TagConsumer} is bound to
 * {@link HttpServletResponse#getWriter() httpServletResponse's writer}.
 * This means that emitting html using returned {@link kotlinx.html.TagConsumer}
 * will result into emitting markup into response
 * <p>
 * Render methods are supposed to have only one argument of {@link kotlinx.html.TagConsumer}
 * type which should be used to emit html into.
 */
public class TagConsumerRenderMethodArgumentResolver
    implements RenderMethodArgumentResolver {
  private final Log log = LogFactory.getLog(TagConsumerRenderMethodArgumentResolver.class);

  @Override
  public boolean supportsArgument(MethodParameter parameter) {
    boolean supports = parameter.getParameterType().isAssignableFrom(HTMLStreamBuilder.class);
    if (log.isTraceEnabled()) {
      log.trace("supportsParameter=\"" + parameter + "\" = " + supports);
    }
    return supports;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, Map<String, ?> model,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
    PrintWriter writer = response.getWriter();
    if (log.isDebugEnabled()) {
      log.debug("Creating htmlStreamBuilder bound to writer=\"" + writer + "\"");
    }
    HTMLStreamBuilder<Writer> htmlStreamBuilder = new HTMLStreamBuilder<>(writer, true, false);
    DelayedConsumer<Writer> consumer = new DelayedConsumer<>(htmlStreamBuilder);
    if (log.isDebugEnabled()) {
      log.debug("Created htmlStreamBuilder=\"" + htmlStreamBuilder + "\"");
      log.debug("Wrapped " + htmlStreamBuilder + "into DelayedConsumer");
    }
    return consumer;
  }
}
