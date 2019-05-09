package com.caco3.equinox.core.render;

import com.caco3.equinox.core.argument.RenderMethodArgumentResolver;
import com.caco3.equinox.core.method.RenderMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * {@link DefaultRenderMethodAdapter} is a {@link RenderMethodAdapter}
 * hiding all logic related to {@link RenderMethod} invocation.
 * <p>
 * It uses {@link RenderMethodArgumentResolver}s to resolve arguments for
 * {@link RenderMethod} to invoke
 * <p>
 * The {@link DefaultRenderMethodAdapter} is immutable class once created.
 * It has no {@code public} constructors.
 * Use {@link Builder} api to create an instance of {@link DefaultRenderMethodAdapter}
 */
public class DefaultRenderMethodAdapter implements RenderMethodAdapter {
  private final Log log = LogFactory.getLog(DefaultRenderMethodAdapter.class);

  private final List<RenderMethodArgumentResolver> argumentResolvers;

  /**
   * Create {@link DefaultRenderMethodAdapter} using {@link Builder}.
   *
   * @param builder to create {@link DefaultRenderMethodAdapter} from
   */
  private DefaultRenderMethodAdapter(Builder builder) {
    this.argumentResolvers = unmodifiableList(asList(builder.argumentResolvers
        .toArray(new RenderMethodArgumentResolver[0])));
  }

  @Override
  public void render(RenderMethod renderMethod, @Nullable Object bean,
                     Map<String, ?> model, HttpServletRequest request,
                     HttpServletResponse response) throws Exception {
    if (log.isDebugEnabled()) {
      log.debug("Rendering view using renderMethod=\"" + renderMethod + "\", bean=\""
          + bean + "\", model=\"" + model + "\", request=\"" + request
          + "\", response=\"" + response + "\"");
    }
    Object[] arguments = prepareArguments(renderMethod, model, request, response);
    if (log.isDebugEnabled()) {
      log.debug("Resolved arguments=\"" + Arrays.toString(arguments) + "\"");
    }
    renderMethod.invoke(bean, arguments);
  }

  private Object[] prepareArguments(RenderMethod renderMethod, Map<String, ?> model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<MethodParameter> parameters = renderMethod.getParameters();
    Object[] arguments = new Object[parameters.size()];
    int i = 0;
    for (MethodParameter parameter : parameters) {
      arguments[i++] = prepareArgument(parameter, model, request, response);
    }
    return arguments;
  }

  private Object prepareArgument(MethodParameter parameter, Map<String, ?> model,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
    for (RenderMethodArgumentResolver argumentResolver : argumentResolvers) {
      if (log.isDebugEnabled()) {
        log.debug("Trying to resolve argument=\"" + parameter
            + "\" using resolver=\"" + argumentResolver + "\"");
      }
      if (argumentResolver.supportsArgument(parameter)) {
        return argumentResolver.resolveArgument(parameter, model, request, response);
      }
    }
    throw new IllegalStateException("Unable to resolve argument=\"" + parameter
        + "\". Tried resolvers=\"" + argumentResolvers + "\"");
  }

  public static class Builder {
    private Set<RenderMethodArgumentResolver> argumentResolvers = new LinkedHashSet<>();

    /**
     * Add an {@link #argumentResolvers} to the collection of resolvers.
     *
     * @param argumentResolver to add
     * @return {@code this}
     * @throws IllegalArgumentException if {@code argumentResolver == null}
     * @throws IllegalStateException    if this {@link Builder} has this {@code argumentResolver
     *                                  }already
     */
    public Builder addResolver(RenderMethodArgumentResolver argumentResolver) {
      Assert.notNull(argumentResolver, "argumentResolver == null");
      boolean added = argumentResolvers.add(argumentResolver);
      if (!added) {
        throw new IllegalStateException(this + " already contains " + argumentResolver);
      }
      return this;
    }

    /**
     * Build {@link DefaultRenderMethodAdapter}
     *
     * @return build {@link DefaultRenderMethodAdapter}
     */
    public DefaultRenderMethodAdapter build() {
      return new DefaultRenderMethodAdapter(this);
    }
  }
}
