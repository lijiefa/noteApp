package com.example.rom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Note::class), version = 2, exportSchema = false)
abstract class NoteDataBase: RoomDatabase(){
    abstract fun getNotesDao(): NoteDao

    companion object{
        private  var INSTANCE: NoteDataBase?=null
        fun getInstance(context: Context): NoteDataBase{
            //if instance is null,then return it
            //if it is,the create the database
            return INSTANCE?:synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    klass = NoteDataBase::class.java,
                    name = "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                //return instance
                instance
            }

        }

    }


}