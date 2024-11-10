package ru.itmo.wisher.api.image.infrastructure

import ru.itmo.wisher.api.image.application.ImageRepository
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URI
import java.util.*

class S3Storage(
    private val s3Client: S3Client,
    private val defaultBucket: String = "wisher-images",
) : ImageRepository {
    override fun uploadImage(image: ByteArray): URI {
        val imgName = UUID.randomUUID().toString()
            this.s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(defaultBucket)
                    .key(
                        imgName,
                    ) // TODO проверка на уникальность, иначе есть вер перезатирания
                    .build(),
                RequestBody.fromBytes(image),
            )
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
            .bucket(defaultBucket)
            .key(imgName)
            .build()).toURI()
    }
}
