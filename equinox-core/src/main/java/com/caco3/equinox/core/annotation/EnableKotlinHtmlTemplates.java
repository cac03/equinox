package com.caco3.equinox.core.annotation;

import com.caco3.equinox.core.config.EquinoxViewConfigurationSupport;
import com.caco3.equinox.core.config.EquinoxViewMvcConfigurationCustomizer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(value = {
    EquinoxViewConfigurationSupport.class,
    EquinoxViewMvcConfigurationCustomizer.class
})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableKotlinHtmlTemplates {
}
