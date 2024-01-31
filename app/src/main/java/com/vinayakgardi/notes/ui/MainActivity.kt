package com.vinayakgardi.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vinayakgardi.notes.R
import com.vinayakgardi.notes.adapter.NoteAdapter
import com.vinayakgardi.notes.database.NoteDatabase
import com.vinayakgardi.notes.databinding.ActivityMainBinding
import com.vinayakgardi.notes.model.Note
import com.vinayakgardi.notes.viewModel.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private var originalNoteList: List<Note> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title ="Notify"


        val noteDao = NoteDatabase.getDatabase(applicationContext).noteDao()
        adapter = NoteAdapter(this)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)


        setRecyclerView()

        binding.btnAddNote.setOnClickListener {
            launchAddNote()
        }

        binding.searchNoteText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {

                if (searchText != null) {
                    adapter.filterNote(searchText)
                }

                return true
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pop_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_all->{
                GlobalScope.launch (Dispatchers.IO){
                    viewModel.deleteAll()
                }
                Toast.makeText(this, "Notes deleted", Toast.LENGTH_SHORT).show()
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
        return  true
    }

    private fun launchAddNote() {
        startActivity(Intent(this@MainActivity, AddUpdateActivity::class.java))
    }

    private fun setRecyclerView() {

        binding.noteRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.noteRecyclerView.adapter = adapter


        viewModel.readAllNote().observe(this@MainActivity) { notes ->
            originalNoteList = notes
            adapter.updateNotesList(notes)
        }
    }
}