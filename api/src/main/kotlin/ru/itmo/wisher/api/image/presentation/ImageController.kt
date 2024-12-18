package ru.itmo.wisher.api.image.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.itmo.wisher.api.image.application.ImageService
import ru.itmo.wisher.api.image.presentation.model.UploadImageResponse

@RestController
@RequestMapping("api/image")
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping
    fun upload(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<UploadImageResponse> {
        val url = imageService.uploadImage(file.bytes)
        val response = UploadImageResponse(url.toString())

        return ResponseEntity.ok(response)
    }
}
