package com.example.giphyapp.domain.models

import java.io.Serializable

data class Data(
    val id: String?,
    val images: Images?,
    val import_datetime: String?,
    val title: String?,
    val url: String?,
    val user: User?,
): Serializable