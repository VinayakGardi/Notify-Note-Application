package com.vinayakgardi.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vinayakgardi.notes.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("delete from note_table")
    suspend fun deleteAll()

    @Query("Select * from note_table order by noteDate desc")
    fun readAllNote() : LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getSingleNote(id: Int): Note
}