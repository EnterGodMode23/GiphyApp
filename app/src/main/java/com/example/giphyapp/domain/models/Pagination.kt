package com.example.giphyapp.domain.models

data class Pagination(
    val count: Int,
    val offset: Int,
    val total_count: Int
)