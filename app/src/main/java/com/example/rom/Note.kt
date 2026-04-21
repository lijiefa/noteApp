package com.example.rom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="note_table")
data class Note(
    @ColumnInfo(name="title_note")
    val title: String,
    val desc: String,
    val priority: Int?,
    @PrimaryKey(autoGenerate=true)
    var id: Int =0

)