package com.caco3.equinox.core.config;

import com.caco3.equinox.core.controller.KFunctionReturnValueHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class KotlinHtmlDslMvcConfigurationSupport implements WebMvcConfigurer {
  private final KFunctionReturnValueHandler kFunctionReturnValueHandler;

  public KotlinHtmlDslMvcConfigurationSupport(KFunctionReturnValueHandler kFunctionReturnValueHandler) {
    this.kFunctionReturnValueHandler = kFunctionReturnValueHandler;
  }

  @Override
  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
    handlers.add(kFunctionReturnValueHandler);
  }
}
