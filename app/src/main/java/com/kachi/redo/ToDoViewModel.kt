package com.kachi.redo

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToDoViewModel:ViewModel() {

    // LiveData to hold the list of ToDo items
    private var toDoList = MutableLiveData<List<ToDo>>()
    val toDoListLiveData: LiveData<List<ToDo>> = toDoList

    fun getToDoList() {
        toDoList.value = ToDoManager.getToDoList().reversed()
    }

    fun addToDoItem(title: String, description: String) {
        ToDoManager.addToDoItem(title, description)
        getToDoList()
    }

    fun deleteToDoItem(id: Int) {
        ToDoManager.deleteToDoItem(id)
        getToDoList()
    }
}