package com.example.paginationpix

data class PixaModel (
    val hits: ArrayList<ImageModel>
)

data class ImageModel (
    val largeImageURL: String,
    val likes: Int
)
