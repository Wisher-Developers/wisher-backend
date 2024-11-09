package ru.itmo.wisher.api.wishes.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.wishes.application.WishlistService
import ru.itmo.wisher.api.wishes.presentation.model.CreateWishlistRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.WishlistResponse
import java.util.UUID

@RestController
@RequestMapping("api/wishlist")
class WishlistController(
    private val wishlistService: WishlistService,
) {

    @PostMapping("create")
    fun create(
        @RequestBody request: CreateWishlistRequestDto,
    ): ResponseEntity<Void> {
        wishlistService.create(request.toDomain())
        return ResponseEntity.noContent().build()
    }

    @GetMapping("{id}")
    fun get(
        @PathVariable id: UUID,
    ): ResponseEntity<WishlistResponse> {
        return ResponseEntity.ok(wishlistService.getById(id).toResponse())
    }

    @GetMapping("/user/{id}")
    fun getByOwnerId(
        @PathVariable id: UUID,
    ): ResponseEntity<List<WishlistResponse>> {
        return ResponseEntity.ok(wishlistService.getByOwnerId(id).map { it.toResponse() })
    }
}
