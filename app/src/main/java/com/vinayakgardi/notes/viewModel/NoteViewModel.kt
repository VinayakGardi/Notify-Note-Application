package com.vinayakgardi.notes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vinayakgardi.notes.database.NoteDao
import com.vinayakgardi.notes.database.NoteDatabase
import com.vinayakgardi.notes.model.Note
import com.vinayakgardi.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var noteList : LiveData<List<Note>>
    private val noteDao : NoteDao
    private val noteRepository : NoteRepository

    init {
        noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        noteList = noteRepository.readAllNote()
    }

    fun readAllNote(): LiveData<List<Note>> {
        return noteList
    }

    fun insertNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            noteRepository.deleteNote(note)
        }
    }

    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            noteRepository.deleteAll()
        }
    }

}