package com.caco3.equinox.core.annotation;

import com.caco3.equinox.core.config.EquinoxViewConfigurationSupport;
import com.caco3.equinox.core.config.EquinoxViewMvcConfigurationCustomizer;
import org.springframework.context.annotation.Import;

@Import(value = {
    EquinoxViewConfigurationSupport.class,
    EquinoxViewMvcConfigurationCustomizer.class
})
public @interface EnableKotlinHtmlTemplates {
}
