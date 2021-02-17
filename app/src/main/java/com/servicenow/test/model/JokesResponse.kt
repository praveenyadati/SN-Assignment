package com.servicenow.test.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JokesResponse(val type: String,val value: Joke) : Parcelable

@Entity(tableName = "Jokes")
@Parcelize
data class Joke(@PrimaryKey(autoGenerate = false) val id: Int, val joke: String?, var firstName : String?, var lastName : String?) : Parcelable
