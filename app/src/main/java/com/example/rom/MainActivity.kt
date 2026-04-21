package com.example.rom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rom.activities.AddEidtActivity
import com.example.rom.adapter.NoteAdapter
import com.example.rom.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() , NoteAdapter.OnClickListener{

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
    private lateinit var getResult: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recycler_view)
        addNoteButton=findViewById(R.id.add_note_button)
        notesAdapter= NoteAdapter(this)
        recyclerView.adapter=notesAdapter
        recyclerView.layoutManager= LinearLayoutManager(this)


        noteViewModel= ViewModelProvider(
            owner = this,
            factory = ViewModelProvider.AndroidViewModelFactory(application)
        )[NoteViewModel::class.java]

        noteViewModel.allNotes.observe(this){
            //here we can add the data to our recyclerView
            Log.d("MainActivity", "观察到 ${it.size} 条数据")
            notesAdapter.submitList(it)
        }
        getResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== Constants.ADD_REQUEST_CODE){
                val title= it.data?.getStringExtra(Constants.EXTRA_TITLE)
                val description= it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                val priority= it.data?.getIntExtra(Constants.EXTRA_PRIORITY,-1)

                if(!title.isNullOrEmpty() && !description.isNullOrEmpty()&&priority!=-1) {
                    val note = Note(title, description, priority)
                    noteViewModel.addNote(note)
                }

            }else if(it.resultCode== Constants.UPDATE_REQUEST_CODE){
                val title= it.data?.getStringExtra(Constants.EXTRA_TITLE)
                val description= it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                val priority= it.data?.getIntExtra(Constants.EXTRA_PRIORITY,-1)
                val id =it.data?.getIntExtra(Constants.EXTRA_ID,-1)
                val note=Note(title!! ,description!!, priority )
                note.id= id!!
                noteViewModel.updateNote(note)
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
                val removeItem=notesAdapter.getNoteAt(viewHolder.bindingAdapterPosition)
                noteViewModel.deleteNote(notesAdapter.getNoteAt(viewHolder.bindingAdapterPosition))

                Snackbar.make(this@MainActivity,recyclerView,"delete note", Snackbar.LENGTH_LONG).setAction("UNDO"){
                    noteViewModel.addNote(removeItem)
                }.show()
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

    override fun onClickItem(note: Note) {
        val title=note.title
        val description=note.desc
        val priority=note.priority
        val id =note.id

        //val note=Note(title ,description, priority)
        val intent= Intent(this@MainActivity, AddEidtActivity::class.java)
        intent.putExtra(Constants.EXTRA_TITLE,title)
        intent.putExtra(Constants.EXTRA_DESCRIPTION,description)
        intent.putExtra(Constants.EXTRA_PRIORITY,priority)
        intent.putExtra(Constants.EXTRA_ID,id)
        getResult.launch(intent)


    }
}

