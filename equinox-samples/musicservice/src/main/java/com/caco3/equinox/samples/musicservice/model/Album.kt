package com.caco3.equinox.samples.musicservice.model

data class Album (
        val producer: String,
        val title: String,
        val songs: List<Song>
)