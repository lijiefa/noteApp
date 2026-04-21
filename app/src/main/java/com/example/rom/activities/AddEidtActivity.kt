package com.example.rom.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rom.R
import com.example.rom.utils.Constants

class AddEidtActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var numberPicker: NumberPicker



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_eidt)

        editTextTitle=findViewById(R.id.edit_text_title)
        editTextDescription=findViewById(R.id.edit_text_description)
        numberPicker=findViewById(R.id.number_picker_priority)
        numberPicker.minValue=0
        numberPicker.maxValue=10

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if(intent.hasExtra(Constants.EXTRA_ID)){
            title="Edit Note"
            editTextTitle.setText(intent.getStringExtra(Constants.EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION))
            numberPicker.value=intent.getIntExtra(Constants.EXTRA_PRIORITY,-1)

        }else {
            title = "Add Note"
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.save_menu_item->{
                saveNote()
            }
        }
        return true
    }

    private fun saveNote() {
        val title=editTextTitle.text.toString()
        val description=editTextTitle.text.toString()
        val priority=numberPicker.value
        //trim删除字符串前面的空格
        if(title.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this,"please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val id=intent.getIntExtra(Constants.EXTRA_ID,-1)
        if(id!=-1){
            setResult(Constants.UPDATE_REQUEST_CODE, Intent().apply {
                putExtra(Constants.EXTRA_TITLE,title)
                putExtra(Constants.EXTRA_DESCRIPTION,description)
                putExtra(Constants.EXTRA_PRIORITY,priority)
                putExtra(Constants.EXTRA_ID,id)})

                finish()

        }


        setResult(Constants.ADD_REQUEST_CODE,Intent().apply {
            putExtra(Constants.EXTRA_TITLE,title)
            putExtra(Constants.EXTRA_DESCRIPTION,description)
            putExtra(Constants.EXTRA_PRIORITY,priority)})
            finish()

    }

}