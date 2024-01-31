package com.vinayakgardi.notes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.notes.R
import com.vinayakgardi.notes.model.Note
import com.vinayakgardi.notes.ui.AddUpdateActivity
import com.vinayakgardi.notes.util.Converter

class NoteAdapter(private val context: Context) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var notesList = ArrayList<Note>()
    private var fullList = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteDate: TextView = itemView.findViewById(R.id.txt_note_date)
        val noteTitle: TextView = itemView.findViewById(R.id.txt_note_title)
        val noteDescription: TextView = itemView.findViewById(R.id.txt_note_description)

    }

    fun updateNotesList(newNotesList: List<Note>) {
        fullList.clear()
        fullList.addAll(newNotesList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun getNoteAtPosition(position: Int): Note {
        return notesList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.noteTitle.text = currentNote.noteTitle
        holder.noteDate.text = Converter.convertLongToTime(currentNote.noteDate)
        holder.noteDescription.text = currentNote.noteDescription.trim()

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, AddUpdateActivity::class.java)
            intent.putExtra("noteTitle", currentNote.noteTitle.toString())
            intent.putExtra("noteDescription", currentNote.noteDescription.toString())
            intent.putExtra("noteDate", currentNote.noteDate.toString())
            intent.putExtra("id", currentNote.id)
            intent.putExtra("UPDATE", 1)

            holder.itemView.context.startActivity(intent)

        }
    }

    fun filterNote(search: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.noteTitle.lowercase().trim()
                    .contains(search.lowercase().trim()) || item.noteDescription.lowercase().trim()
                    .contains(search.lowercase().trim())
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }
}