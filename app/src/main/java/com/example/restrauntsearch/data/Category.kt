package com.example.restrauntsearch.data

import com.google.gson.annotations.SerializedName

data class Category(
    val id: String,
    @SerializedName("menu-items")
    val menuitems: List<MenuItem>,
    val name: String
)