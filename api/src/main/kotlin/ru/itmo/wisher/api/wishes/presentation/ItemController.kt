package ru.itmo.wisher.api.wishes.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.wishes.application.ItemService
import ru.itmo.wisher.api.wishes.presentation.model.CopyItemRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.CreateItemRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.ItemResponse
import ru.itmo.wisher.api.wishes.presentation.model.UpdateItemRequestDto
import java.util.UUID

@RestController
@RequestMapping("api/item")
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping("create")
    fun create(
        @RequestBody request: CreateItemRequestDto,
    ): ResponseEntity<ItemResponse> {
        return ResponseEntity.ok(itemService.create(request.toDomain()).toResponse())
    }

    @PostMapping("copy")
    fun copy(
        @RequestBody request: CopyItemRequestDto,
    ): ResponseEntity<ItemResponse> {
        return ResponseEntity.ok(itemService.copy(request.toDomain()).toResponse())
    }

    @PostMapping("delete/{id}")
    fun delete(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        itemService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("{id}")
    fun get(
        @PathVariable id: UUID,
    ): ResponseEntity<ItemResponse> {
        return ResponseEntity.ok(itemService.getById(id).toResponse())
    }

    @PostMapping("update")
    fun update(
        @RequestBody request: UpdateItemRequestDto,
    ): ResponseEntity<ItemResponse> {
        return ResponseEntity.ok(itemService.update(request.toDomain()).toResponse())
    }

    @GetMapping("recommendations")
    fun getRecommendations(): ResponseEntity<List<ItemResponse>> {
        return ResponseEntity.ok(itemService.getRecommendations().map { it.toResponse() })
    }
}
