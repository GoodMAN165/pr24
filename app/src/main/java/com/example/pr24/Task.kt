package com.example.pr24

data class Task(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)