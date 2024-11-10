package ru.itmo.wisher.api.image.application

import org.springframework.stereotype.Component
import java.net.URI

@Component
class ImageService(
    private val imageRepository: ImageRepository,
) {
    fun uploadImage(image: ByteArray): URI {
        return this.imageRepository.uploadImage(image)
    }
}
