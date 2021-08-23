package com.example.movieseeker.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

const val LANGUAGE_DEFAULT = ""
const val LANGUAGE_ENGLISH = "en"
const val LANGUAGE_RUSSIAN = "ru"
@Parcelize
data class Movie(
        val id: Int = -1,
        val language: String = LANGUAGE_DEFAULT,
        val name: String = getDefaultName(),
        val creationDate: String? = null,
        val rating: Float = 0.0F,
        val picture: String? = null

): Parcelable

fun getDefaultMovie() = Movie()

fun getWorldMovies() = listOf(
        Movie(436969, LANGUAGE_ENGLISH,"Отряд самоубийц: Миссия навылет", "2021", 81F),
        Movie(451048, LANGUAGE_ENGLISH,"Круиз по джунглям", "2021", 79F),
        Movie(706972, LANGUAGE_ENGLISH,"Война наркокортелей", "2021", 25F),
        Movie(550988, LANGUAGE_ENGLISH,"Главный герой", "2021", 79F),
        Movie(379686, LANGUAGE_ENGLISH,"Космический джем: Новое поколение", "2021", 75F)
)

fun getRussianMovies() = listOf(
        Movie(529106, LANGUAGE_RUSSIAN,"Майор Гром: Чумной Доктор", "2021", 69F),
        Movie(575088, LANGUAGE_RUSSIAN,"Яга. Кошмар тёмного леса", "2020", 60F),
        Movie(797394, LANGUAGE_RUSSIAN,"Гензель, Греттель и Агенство Магии", "2021", 72F),
        Movie(574982, LANGUAGE_RUSSIAN,"Аванпост", "2019", 65F),
        Movie(594718, LANGUAGE_RUSSIAN,"Спутник", "2020", 65F)
)

fun getDefaultName() = "NoNameMovie"

