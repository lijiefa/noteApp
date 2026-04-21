package com.example.rom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rom.activities.AddEidtActivity
import com.example.rom.adapter.NoteAdapter
import com.example.rom.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

//    private val factory: ViewModelProvider.Factory=object : ViewModelProvider.Factory{
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return NoteViewModel(application) as T
//        }
//    }

  // private var noteViewModel: NoteViewModel by viewModels { factory }

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NoteAdapter
    private lateinit var addNoteButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recycler_view)
        addNoteButton=findViewById(R.id.add_note_button)
        notesAdapter= NoteAdapter()
        recyclerView.adapter=notesAdapter
        recyclerView.layoutManager= LinearLayoutManager(this)


        noteViewModel= ViewModelProvider(
            owner = this,
            factory = ViewModelProvider.AndroidViewModelFactory(application)
        )[NoteViewModel::class.java]

        noteViewModel.allNotes.observe(this){
            //here we can add the data to our recyclerView
            Log.d("MainActivity", "观察到 ${it.size} 条数据")
            notesAdapter.setNotes(it)
        }
        val getResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== Constants.REQUEST_CODE){
                val title= it.data?.getStringExtra(Constants.EXTRA_TITLE)
                val description= it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                val priority= it.data?.getIntExtra(Constants.EXTRA_PRIORITY,-1)

                if(!title.isNullOrEmpty() && !description.isNullOrEmpty()&&priority!=-1) {
                    val note = Note(title, description, priority)
                    noteViewModel.addNote(note)
                }

            }
        }

        addNoteButton.setOnClickListener {
            val intent= Intent(this@MainActivity, AddEidtActivity::class.java)
            getResult.launch(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                noteViewModel.deleteNote(notesAdapter.getNoteAt(viewHolder.bindingAdapterPosition))
            }

        }).attachToRecyclerView(recyclerView)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_all_notes_menu->{
                noteViewModel.deleteAllNotes()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}

