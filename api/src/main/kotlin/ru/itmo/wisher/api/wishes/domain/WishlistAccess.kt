package ru.itmo.wisher.api.wishes.domain

import ru.itmo.wisher.api.user.domain.User

data class WishlistAccess(
    var wishlist: Wishlist,
    var user: User,
)
