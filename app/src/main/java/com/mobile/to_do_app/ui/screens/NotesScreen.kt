package com.mobile.to_do_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.to_do_app.DestinationScreen
import com.mobile.to_do_app.ui.components.Header
import com.mobile.to_do_app.ui.theme.LightBackground
import com.mobile.to_do_app.utils.navigateTo
import com.mobile.to_do_app.viewmodels.TodoViewModel

@Composable
fun NotesScreen(
    todoViewModel: TodoViewModel,
    navController: NavController
) {
    val todos by todoViewModel.todos.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            Header(navController = navController, header = "Notlar")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

                todoViewModel.addTodo("Yeni Not") { newTodo ->
                    if (newTodo != null) {
                        todoViewModel.getTodoById(newTodo.id.toString())
                        navigateTo(navController,DestinationScreen.Note.createRoute(newTodo.id.toString()))
                    } else {
                        Log.e("YeniTodo", "Ekleme başarısız!")
                    }
                }
            }) {
                Text("Yeni Not", modifier = Modifier.padding(16.dp))
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBackground)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(todos) { todo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    todoViewModel.getTodoById(todo.id.toString())
                                    navigateTo(navController,DestinationScreen.Note.createRoute(todo.id.toString()))
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = todo.text.take(15))
                                IconButton(onClick = {
                                    todo.id?.let { todoViewModel.deleteTodo(it) }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Sil"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
