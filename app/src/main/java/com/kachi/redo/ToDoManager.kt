package com.kachi.redo

import java.time.Instant
import java.util.Date

object ToDoManager {
    //
    private var toDoList = mutableListOf<ToDo>()

    fun getToDoList(): List<ToDo> {
        return toDoList
    }

    fun addToDoItem(title: String, description: String) {
        toDoList.add(ToDo(
            toDoList.size + 1,
            title,
            description,
            Date.from(Instant.now())
        ))
    }

    fun deleteToDoItem(id: Int) {
        toDoList.removeIf { it.id == id }
    }
}