package com.caco3.equinox.samples.musicservice

import com.caco3.equinox.core.annotation.EnableKotlinHtmlTemplates
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableKotlinHtmlTemplates
open class MusicApplication(
        private val applicationContext: ApplicationContext
): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        applicationContext.beanDefinitionNames.forEach {
            println(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<MusicApplication>(*args)
}