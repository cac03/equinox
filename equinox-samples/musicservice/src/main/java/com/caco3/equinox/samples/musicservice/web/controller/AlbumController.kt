package com.caco3.equinox.samples.musicservice.web.controller

import com.caco3.equinox.samples.musicservice.model.Album
import com.caco3.equinox.samples.musicservice.model.Song
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Duration

@Controller
class AlbumController {
    @RequestMapping("/albums/floabn")
    fun forLackOfABetterName(model: Model): String {
        val songs = listOf(
                Song("FML", Duration.ofSeconds(6 * 60 + 35)),
                Song("Moar Ghosts 'n' Stuff", Duration.ofSeconds(4 * 60 + 30)),
                Song("Ghosts 'n' Stuff", Duration.ofSeconds(3 * 60 + 15)),
                Song("Hi Friend!", Duration.ofSeconds(3 * 60 + 15)),
                Song("Bot", Duration.ofSeconds(5 * 60 + 22)),
                Song("Word Problems", Duration.ofSeconds(7 * 60 + 48)),
                Song("Soma", Duration.ofSeconds(6 * 60 + 7)),
                Song("Lack of a Better Name", Duration.ofSeconds(6 * 60 + 58)),
                Song("The 16th Hour", Duration.ofSeconds(9 * 60 + 29)),
                Song("Strobe", Duration.ofSeconds(10 * 60 + 37))
        )
        val album = Album("deadmau5", "For Lack of a Better Name", songs)
        model["album"] = album
        return "album"
    }
}