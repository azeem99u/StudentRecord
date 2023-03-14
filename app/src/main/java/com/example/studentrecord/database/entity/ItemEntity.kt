package com.example.studentrecord.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey
    val name:String ="",
    val email:String ="",
    val class_:String ="",
    val location:String ="",
    val dateOfBirth:String = ""
)
