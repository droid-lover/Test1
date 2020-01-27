package com.vs.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created By Sachin
 */

@Entity(indices = arrayOf(Index(value = ["title", "description"], unique = false)))
data class User(
        @ColumnInfo
        var title: String,
        @ColumnInfo
        var description: String,
        @ColumnInfo
        var time: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}