package com.kachi.redo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kachi.redo.database.DAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ToDoViewModel:ViewModel() {

    val toDoDAO = MainApplication.instance.getDAO() // get the DAO object from the database

    val toDoListLiveData: LiveData<List<ToDo>> = toDoDAO.getAllToDo()


    fun addToDoItem(title: String, description: String) {

        viewModelScope.launch(Dispatchers.IO) {
            toDoDAO.addToDo(ToDo(
                title = title,
                description = description,
                time = Date.from(Instant.now())
            ))
        }
    }

    fun deleteToDoItem(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            toDoDAO.deleteToDo(id)
        }
    }
}