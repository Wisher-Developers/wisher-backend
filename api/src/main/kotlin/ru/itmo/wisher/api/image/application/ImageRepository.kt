package ru.itmo.wisher.api.image.application

import java.net.URI

interface ImageRepository {

    fun uploadImage(image: ByteArray): URI
}
