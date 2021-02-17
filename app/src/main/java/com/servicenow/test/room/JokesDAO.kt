package com.servicenow.test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.servicenow.test.model.Joke

@Dao
interface JokesDAO {

    @Query("SELECT * FROM Jokes")
    fun findAll(): Array<Joke>

    @Insert
    fun insert(joke: Joke)
}