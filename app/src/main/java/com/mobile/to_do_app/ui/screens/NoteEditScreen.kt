package com.mobile.to_do_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.to_do_app.DestinationScreen
import com.mobile.to_do_app.ui.components.Loading
import com.mobile.to_do_app.utils.navigateTo
import com.mobile.to_do_app.viewmodels.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navController: NavController,
    todoViewModel: TodoViewModel,
     id : String
) {


    val loading = todoViewModel.setLoad.value
    val todo = todoViewModel.selectedTodo.collectAsState()

    val text = remember {
        mutableStateOf(todo.value?.text.orEmpty())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        todoViewModel.updateTodo(id,text.value){
                            navigateTo(
                                navController,
                                DestinationScreen.Notes.route
                            )
                        }

                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        todoViewModel.updateTodo(id,text.value){}
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Kaydet")
                    }
                }
            )
        }
    ) { padding ->

        if(loading){
            Loading()
        }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = "Title",
                onValueChange = {  },
                //placeholder = { Text("Başlık") },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = text.value,
                onValueChange = {text.value=it},
                placeholder = { Text("Not") },
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                maxLines = Int.MAX_VALUE
            )
        }
    }}
}
