package com.kachi.redo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kachi.redo.ToDo

@Dao
interface DAO {

    // Get all ToDo items from the database by descending order of id or latest first
    @Query("SELECT * FROM ToDo ORDER BY id DESC")
    fun getAllToDo():LiveData<List<ToDo>>

    @Insert
    fun addToDo(toDo: ToDo)

    @Query("DELETE FROM ToDo WHERE id = :id")
    fun deleteToDo(id: Int)
}