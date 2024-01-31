package com.vinayakgardi.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vinayakgardi.notes.databinding.ActivityAddUpdateBinding
import com.vinayakgardi.notes.model.Note
import com.vinayakgardi.notes.util.Converter
import com.vinayakgardi.notes.viewModel.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val UpdateID = intent.getIntExtra("UPDATE", -1)
        val viewModel: NoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        if (UpdateID == 1) {
            updateNote(viewModel)

        } else {
            addNote(viewModel)
        }

        binding.btnDelete.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString()
            val noteDescription = binding.editNoteDescription.text.toString()
            var noteDate = intent.getStringExtra("noteDate")
            noteDate = noteDate?.toLong().toString()
            val noteId = intent.getIntExtra("id", -1)


            val note: Note = Note(noteId, noteTitle, noteDescription, noteDate.toLong())

            deleteNote(note , viewModel)
        }

    }

    private fun updateNote(viewModel: NoteViewModel) {

        binding.AddUpdateTextview.setText("Update Note")
        binding.btnDelete.visibility = View.VISIBLE


        val oldNoteTitle = intent.getStringExtra("noteTitle")
        val oldNoteDescription = intent.getStringExtra("noteDescription")
        var oldNoteDate = intent.getStringExtra("noteDate")
        val oldNoteId = intent.getIntExtra("id", -1)
        oldNoteDate = oldNoteDate?.toLong().toString()

        binding.editNoteTitle.setText(oldNoteTitle)
        binding.editNoteDescription.setText(oldNoteDescription)

        binding.btnSave.setOnClickListener {
            if (binding.editNoteTitle.text.trim().isEmpty()) {
                binding.editNoteTitle.error = "Enter the Field"
                return@setOnClickListener
            }
            if (binding.editNoteDescription.text.trim().isEmpty()) {
                binding.editNoteDescription.error = "Enter the Field"
                return@setOnClickListener
            }

            val newNoteTitle = binding.editNoteTitle.text.toString()
            val newNoteDescription = binding.editNoteDescription.text.toString()

            val note: Note = Note(oldNoteId, newNoteTitle, newNoteDescription, oldNoteDate.toLong())

            GlobalScope.launch(Dispatchers.IO) {
                viewModel.updateNote(note)
            }



            Toast.makeText(this@AddUpdateActivity, "Note Updated", Toast.LENGTH_SHORT).show()


            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }

    private fun deleteNote(note: Note, viewModel: NoteViewModel) {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.deleteNote(note)

        }
        Toast.makeText(this@AddUpdateActivity, "Note deleted", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))

    }


    private fun addNote(viewModel: NoteViewModel) {

        binding.btnDelete.visibility = View.GONE
        binding.AddUpdateTextview.setText("Add Note")
        binding.btnSave.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString()
            val noteDescription = binding.editNoteDescription.text.toString()
            val noteDate = Converter.currentTimeToLong()

            if (noteTitle.trim().isEmpty()) {
                binding.editNoteTitle.error = "Enter the Field"
                return@setOnClickListener
            }
            if (noteDescription.trim().isEmpty()) {
                binding.editNoteDescription.error = "Enter the Field"
                return@setOnClickListener
            }
            val note: Note = Note(0, noteTitle, noteDescription, noteDate)

            GlobalScope.launch {
                viewModel.insertNote(note)
            }

            Toast.makeText(this@AddUpdateActivity, "Note Added", Toast.LENGTH_SHORT).show()


            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }
}