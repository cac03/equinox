package com.caco3.equinox.samples.musicservice.web.view

import com.caco3.equinox.samples.musicservice.model.Album
import kotlinx.html.TagConsumer
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.section
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import org.springframework.web.bind.annotation.ModelAttribute
import java.time.Duration

private fun Duration.format(): String {
    val minutes = toMinutes()
    val seconds = seconds % 60
    return if (seconds > 10) {
        "$minutes:$seconds"
    } else {
        "$minutes:0$seconds"
    }
}

fun renderAlbum(
        create: TagConsumer<*>,
        @ModelAttribute("album") album: Album
) = create.html {
    head {
        title = album.title
        link {
            href = "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.4/css/bulma.min.css"
            rel = "stylesheet"
        }
    }
    body {
        section(classes = "hero is-primary") {
            div(classes = "hero-body") {
                div(classes = "container") {
                    h1(classes = "title") { +album.producer }
                    h2(classes = "subtitle") { +album.title }
                }
            }
        }

        div("container content") {
            ul {
                album.songs.forEach {
                    li {
                        +it.title
                        +" "
                        strong { +it.duration.format() }
                    }
                }
            }
        }
    }
}