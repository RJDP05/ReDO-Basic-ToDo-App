package com.kachi.redo


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ToDoList(
    viewModel: ToDoViewModel
) {
    // Get the list of ToDo items from the ViewModel
    val toDoList by viewModel.toDoListLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(modifier = Modifier
            .padding(8.dp),
            horizontalAlignment = Alignment.End) {

            var titleText by remember { mutableStateOf("") }
            val charLimit = 15
            var descriptionText by remember { mutableStateOf("") }
            val descriptionCharLimit = 40

            // FocusRequester is used to request focus on a particular TextField
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

            TextField(
                value = titleText,
                onValueChange = {
                    if (it.length <= charLimit){
                        titleText = it
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .focusRequester(focusRequester)
                    .padding(8.dp),
                label = { Text(text = "Title") },

                // Set the keyboard action to Next
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                // Set the keyboard action to move focus to the next TextField
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            TextField(
                value = descriptionText,
                onValueChange = {
                    if (it.length <= descriptionCharLimit){
                        descriptionText = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .focusRequester(focusRequester)
                    .padding(8.dp),
                label = { Text("Description") },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )

            Button(
                onClick = {
                    if(titleText.isNotEmpty() && descriptionText.isNotEmpty()){

                        // Request focus on the title TextField
                        focusRequester.requestFocus()
                        // Add the ToDo item to the list
                        viewModel.addToDoItem(titleText, descriptionText)
                    }
                }) {
                Text(text = "Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        toDoList?.let {
            LazyColumn(
                content = {
                    //itemsIndexed is used to display the list of ToDo items
                    itemsIndexed(it){
                            index: Int,
                            item: ToDo -> TodoItem(item = item, onDelete = {
                                viewModel.deleteToDoItem(item.id)
                    })
                    }
                }
            )
        }?: Text( // Display a message if there are no items in the list
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Items to Display",
            fontSize = 26.sp
        )
    }
}

@Composable
fun TodoItem(item: ToDo, onDelete: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/MM/yyyy", Locale.ENGLISH).format(
                    item.time),
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.description,
                maxLines = 10,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                painter = painterResource(
                id = R.drawable.delete),
                contentDescription ="Delete",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}
