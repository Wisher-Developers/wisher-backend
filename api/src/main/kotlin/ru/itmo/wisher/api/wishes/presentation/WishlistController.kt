package ru.itmo.wisher.api.wishes.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.wishes.application.WishlistService
import java.util.UUID

@RestController
@RequestMapping("api/wishlist")
class WishlistController(
    private val wishlistService: WishlistService,
) {

    @PostMapping("create")
    suspend fun create(
        @RequestBody request: CreateWishlistRequestDto,
    ): ResponseEntity<Void> {
        try {
            wishlistService.create(request.toDomain())
            return ResponseEntity.noContent().build()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    @GetMapping("{id}")
    suspend fun get(
        @PathVariable id: UUID,
    ): ResponseEntity<WishlistResponse> {
        try {
            return ResponseEntity.ok(wishlistService.getById(id).toResponse())
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}
