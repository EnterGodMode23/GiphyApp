package com.example.giphyapp.domain.models

data class Gifs(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)