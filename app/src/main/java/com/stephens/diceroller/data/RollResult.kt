package com.stephens.diceroller.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rollResults")
data class RollResult(
    @PrimaryKey val time: Long,
    val result: Int,
    val sides: Int
)