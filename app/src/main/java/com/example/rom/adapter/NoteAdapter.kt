package com.example.rom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rom.Note
import com.example.rom.R
import com.example.rom.adapter.NoteAdapter.*

class NoteAdapter(val listener: OnClickListener):
    ListAdapter<Note, NoteViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK=object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem==newItem
            }

        }
    }
      //  private var notesList: MutableList<Note> = mutableListOf()
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
        val note=getItem(position)
        holder.textTitle.text=note.title
        holder.textViewDescription.text=note.desc
        holder.textViewPriority.text=note.priority.toString()
    }

   // override fun getItemCount(): Int =notesList.size
//    fun setNotes(notes: MutableList<Note>){
//        notesList.clear()            // ✅ 先清空
//        notesList.addAll(notes)
//        notifyDataSetChanged()
//    }
    fun getNoteAt(position: Int):Note{
        return getItem(position)
    }



    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){
         val textTitle: TextView = view.findViewById(R.id.text_view_title)
         val textViewDescription=view.findViewById<TextView>(R.id.text_view_description)
         val textViewPriority=view.findViewById<TextView>(R.id.text_view_priority)
        init {
            view.setOnClickListener {
                if(bindingAdapterPosition!= RecyclerView.NO_POSITION){
                    listener.onClickItem(getItem(bindingAdapterPosition))
                }

            }

        }
    }

    interface OnClickListener{
        fun onClickItem(note: Note)
    }
}