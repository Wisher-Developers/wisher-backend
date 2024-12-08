package ru.itmo.wisher.api.kafka.infrastructure

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class NewWishItemMessage(
    val uuid: UUID,
    val content: String,
)

data class SuggestMatchingWishItemsMessage(
    @JsonProperty("request_id")
    val requestId: UUID,
    val products: List<UUID>,
)

data class SuggestMatchingWishItemResponse(
    @JsonProperty("request_id")
    val requestId: UUID,
    @JsonProperty("products")
    val products: List<UUID>,
    @JsonProperty("response")
    val response: String,
)
