package ru.itmo.wisher.api.wishes.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.wishes.application.ItemService
import java.util.UUID

@RestController
@RequestMapping("api/item")
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping("create")
    fun create(
        @RequestBody request: CreateItemRequestDto,
    ): ResponseEntity<Void> {
        try {
            itemService.create(request.toDomain())
            return ResponseEntity.noContent().build()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    @PostMapping("copy")
    fun copy(
        @RequestBody request: CopyItemRequestDto,
    ): ResponseEntity<Void> {
        try {
            itemService.copy(request.toDomain())
            return ResponseEntity.noContent().build()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    @GetMapping("{id}")
    fun get(
        @PathVariable id: UUID,
    ): ResponseEntity<ItemResponse> {
        try {
            return ResponseEntity.ok(itemService.getById(id).toResponse())
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}
