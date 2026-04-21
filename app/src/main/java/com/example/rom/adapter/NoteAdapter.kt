package com.example.rom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rom.Note
import com.example.rom.R

class NoteAdapter():
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
        private var notesList: MutableList<Note> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
         return NoteViewHolder(
             LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
         )

    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note=notesList[position]
        holder.textTitle.text=note.title
        holder.textViewDescription.text=note.desc
        holder.textViewPriority.text=note.priority.toString()
    }

    override fun getItemCount(): Int =notesList.size
    fun setNotes(notes: MutableList<Note>){
        notesList.clear()            // ✅ 先清空
        notesList.addAll(notes)
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){
         val textTitle: TextView = view.findViewById(R.id.text_view_title)
         val textViewDescription=view.findViewById<TextView>(R.id.text_view_description)
         val textViewPriority=view.findViewById<TextView>(R.id.text_view_priority)
    }
}