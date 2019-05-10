package com.caco3.equinox.core.method;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * Convenient wrapper for actual render method.
 */
public class RenderMethod {
  /**
   * The target method
   */
  private final Method method;

  public RenderMethod(Method method) {
    Assert.notNull(method, "method == null");
    this.method = method;
  }

  /**
   * Return list target method parameters as {@link MethodParameter}s
   * The returned list is read-only
   *
   * @return target method parameters
   */
  public List<MethodParameter> getParameters() {
    return Arrays.stream(method.getParameters())
        .map(RenderMethod::toMethodParameter)
        .collect(collectingAndThen(toList(), Collections::unmodifiableList));
  }

  private static MethodParameter toMethodParameter(Parameter parameter) {
    return SynthesizingMethodParameter.forParameter(parameter);
  }

  /**
   * Invoke target method on given {@code target} with {@code args}
   *
   * @param target to invoke method on
   * @param args   to invoke method with
   * @return return value of target method
   * @throws IllegalArgumentException if {@code args == null}
   * @throws IllegalArgumentException if target method is not static
   *                                  but {@code target} is {@code null}
   */
  public Object invoke(@Nullable Object target, Object... args) {
    Assert.notNull(args, "args == null");
    if (target == null) {
      Assert.isTrue(Modifier.isStatic(method.getModifiers()),
          () -> "target == null but method = \"" + method + "\" is not static");
    }
    return ReflectionUtils.invokeMethod(method, target, args);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(method);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != RenderMethod.class) {
      return false;
    }
    RenderMethod other = (RenderMethod) obj;
    return Objects.equals(method, other.method);
  }

  @Override
  public String toString() {
    return "RenderMethod(targetMethod=" + method + ")";
  }
}
