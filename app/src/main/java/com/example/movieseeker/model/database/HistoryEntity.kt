package com.example.movieseeker.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieseeker.model.entities.getDefaultName

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val language: String,
    val name: String,
    val creationDate: String,
    val rating: Float,
    val picture: String?
)
