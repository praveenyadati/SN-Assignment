package com.servicenow.test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.servicenow.test.model.Joke

@Database(entities = [Joke::class], version = 2, exportSchema = false)

abstract class JokesDataBase : RoomDatabase() {
    abstract val jokesDao: JokesDAO
}