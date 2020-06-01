package com.hassan.library.models

import java.io.Serializable

data class Book (
    val id:Int,
    val book_name : String,
    val book_img : String,
    val count : Int,
    val author : String
):Serializable
