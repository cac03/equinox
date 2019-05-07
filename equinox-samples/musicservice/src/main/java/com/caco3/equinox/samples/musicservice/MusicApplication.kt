package com.caco3.equinox.samples.musicservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MusicApplication

fun main(args: Array<String>) {
    runApplication<MusicApplication>(*args)
}