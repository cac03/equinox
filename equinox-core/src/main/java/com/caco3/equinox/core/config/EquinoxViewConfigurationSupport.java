package com.caco3.equinox.core.config;

import com.caco3.equinox.core.argument.ModelAttributeRenderMethodArgumentResolver;
import com.caco3.equinox.core.argument.TagConsumerRenderMethodArgumentResolver;
import com.caco3.equinox.core.controller.KFunctionReturnValueHandler;
import com.caco3.equinox.core.render.DefaultRenderMethodAdapter;
import com.caco3.equinox.core.render.RenderMethodAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EquinoxViewConfigurationSupport {
  @Bean
  RenderMethodAdapter renderMethodAdapter() {
    return new DefaultRenderMethodAdapter.Builder()
        .addResolver(modelAttributeRenderMethodArgumentResolver())
        .addResolver(tagConsumerRenderMethodArgumentResolver())
        .build();
  }

  @Bean
  ModelAttributeRenderMethodArgumentResolver modelAttributeRenderMethodArgumentResolver() {
    return new ModelAttributeRenderMethodArgumentResolver();
  }

  @Bean
  TagConsumerRenderMethodArgumentResolver tagConsumerRenderMethodArgumentResolver() {
    return new TagConsumerRenderMethodArgumentResolver();
  }

  @Bean
  KFunctionReturnValueHandler kFunctionReturnValueHandler() {
    return new KFunctionReturnValueHandler(renderMethodAdapter());
  }
}
