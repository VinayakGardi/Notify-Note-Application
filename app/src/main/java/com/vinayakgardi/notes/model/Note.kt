package com.vinayakgardi.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    var noteTitle : String,
    var noteDescription : String ,
    var noteDate : Long
)  : Serializable