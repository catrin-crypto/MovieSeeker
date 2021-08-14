package com.example.movieseeker.model.entities

import android.graphics.Canvas
import java.io.InputStream

data class Movie (
    val name : String = getDefaultName(),
    val creationDate : Int = getDefaultDate(),
    val rating : Float = 0.0F,
    val picture : InputStream? = null
)

fun getDefaultName() = "NoNameMovie"
fun getDefaultDate() = -1
