package com.caco3.equinox.core.annotation;

import com.caco3.equinox.core.config.KotlinHtmlDslConfigurationSupport;
import com.caco3.equinox.core.config.KotlinHtmlDslMvcConfigurationSupport;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(value = {
    KotlinHtmlDslConfigurationSupport.class,
    KotlinHtmlDslMvcConfigurationSupport.class
})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableKotlinHtmlDsl {
}
