package com.caco3.equinox.core.controller;

import com.caco3.equinox.core.method.RenderMethod;
import com.caco3.equinox.core.render.RenderMethodAdapter;
import com.caco3.equinox.core.view.RenderMethodView;
import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.View;

import java.lang.reflect.Method;

/**
 * {@link KFunctionReturnValueHandler} is a {@link HandlerMethodReturnValueHandler}
 * for {@link kotlin.reflect.KFunction} values.
 * <p>
 * It allows for {@link org.springframework.stereotype.Controller} methods to return
 * a reference to a function to perform rendering.
 */
public class KFunctionReturnValueHandler implements HandlerMethodReturnValueHandler {
  private Log log = LogFactory.getLog(KFunctionReturnValueHandler.class);

  private final RenderMethodAdapter renderMethodAdapter;

  public KFunctionReturnValueHandler(RenderMethodAdapter renderMethodAdapter) {
    this.renderMethodAdapter = renderMethodAdapter;
  }

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    return KFunction.class.isAssignableFrom(returnType.getParameterType());
  }

  @Override
  public void handleReturnValue(
      Object returnValue,
      MethodParameter returnType,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest) throws Exception {
    if (log.isDebugEnabled()) {
      log.debug("Handling return value=\"" + returnValue + "\"");
    }
    KFunction<?> function = (KFunction<?>) returnValue;
    Method method = ReflectJvmMapping.getJavaMethod(function);
    if (log.isDebugEnabled()) {
      log.debug("Mapped KFunction=\"" + function + "\" to method=\"" + method + "\"");
    }
    View view = new RenderMethodView(new RenderMethod(method), null, renderMethodAdapter);
    mavContainer.setView(view);
    if (log.isDebugEnabled()) {
      log.debug("Set view=\"" + view + "\" to mavContainer=\"" + mavContainer + "\"");
    }
  }
}
