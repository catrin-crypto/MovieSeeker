package com.example.movieseeker.model.entities

import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.Image
import android.os.Parcelable
import java.io.InputStream
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Movie (
    val name : String = getDefaultName(),
    val creationDate : Int = getDefaultDate(),
    val rating : Float = 0.0F,
    val picture : Bitmap? = null
): Parcelable

fun getDefaultMovie() = Movie()

fun getWorldMovies() = listOf(
        Movie("Солдаты-зомби",2021,75F),
        Movie("Гнев человеческий",2021,78F),
        Movie("Война будущего",2021,82F),
        Movie("Токийские мстители",2021,89F),
        Movie("Хищные птицы: Потрясающая история Харли Квинн",2020,71F)
)

fun getRussianMovies() = listOf(
        Movie("Брат",1997,82F),
        Movie("Брат 2", 2000, 81F),
        Movie("Маша и медведь", 2009, 72F),
        Movie("Легенда №17", 2012,80F),
        Movie("Бригада", 2002,83F)
)

fun getDefaultName() = "NoNameMovie"
fun getDefaultDate() = -1
