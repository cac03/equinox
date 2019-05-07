package com.caco3.equinox.samples.musicservice.web.view

import com.caco3.equinox.core.annotation.ViewComponent
import com.caco3.equinox.core.annotation.ViewMapping
import com.caco3.equinox.samples.musicservice.model.Album
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.link
import kotlinx.html.section
import kotlinx.html.stream.appendHTML
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import org.springframework.web.bind.annotation.ModelAttribute

@ViewComponent
class AlbumView {
    @ViewMapping(name = "album")
    fun renderAlbum(
            appendable: Appendable,
            @ModelAttribute("model") album: Album
    ) {
        appendable.appendHTML().html {
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

                div("content") {
                    ul {
                        album.songs.forEach {
                            +it.title
                            strong { +it.duration.toString() }
                        }
                    }
                }
            }
        }
    }
}