package com.vinayakgardi.notes.repository

import androidx.lifecycle.LiveData
import com.vinayakgardi.notes.database.NoteDao
import com.vinayakgardi.notes.model.Note

class NoteRepository(val noteDao: NoteDao) {

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun deleteAll() = noteDao.deleteAll()

    fun readAllNote(): LiveData<List<Note>> = noteDao.readAllNote()

    suspend fun getSingleNote(id: Int): Note =  noteDao.getSingleNote(id)


}