package com.hassan.library.models

import java.io.Serializable

class User (
    val id:Int?,
    val username : String,
    val password : String,
    val image :String,
    val borrowedCounts:Int
):Serializable