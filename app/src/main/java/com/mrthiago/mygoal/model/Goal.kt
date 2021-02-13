package com.mrthiago.mygoal.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.net.URI
import java.util.*

// Model -> DB Table
@Parcelize
@Entity(tableName = "goal_table")
data class Goal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val creationDate: Date?
    ) : Parcelable