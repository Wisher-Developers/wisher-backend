package ru.itmo.wisher.api.image.infrastructure

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3StorageConfiguration {

    @Bean
    fun S3Storage(
        @Value("\${s3-storage.host}") host: String,
        @Value("\${s3-storage.access-id}") accessId: String,
        @Value("\${s3-storage.secret-key}") secretKey: String,
    ): S3Storage {
        return S3Storage(
            S3Client.builder()
                .region(Region.of("ru-central1"))
                .endpointOverride(URI(host))
                .credentialsProvider {
                    object : AwsCredentials {
                        override fun accessKeyId(): String {
                            return accessId
                        }

                        override fun secretAccessKey(): String {
                            return secretKey
                        }
                    }
                }
                .build(),
        )
    }
}
