package com.example.pr24

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task>() // Изменяемый список задач

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList.add(Task("Сдать Практическую №24", "Дописать код и сделать скриншоты", true))
        taskList.add(Task("Купить продукты", "Молоко, хлеб, яйца"))
        taskList.add(Task("Позвонить маме", "Спросить как дела"))

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Элементы идут сверху вниз

        adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeTask(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LinearLayout(this)
        dialogView.orientation = LinearLayout.VERTICAL
        dialogView.setPadding(50, 40, 50, 10)

        val titleInput = EditText(this)
        titleInput.hint = "Название задачи"
        dialogView.addView(titleInput)

        val descInput = EditText(this)
        descInput.hint = "Описание задачи"
        dialogView.addView(descInput)

        AlertDialog.Builder(this)
            .setTitle("Новая задача")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val title = titleInput.text.toString()
                val desc = descInput.text.toString()
                if (title.isNotEmpty()) {
                    adapter.addTask(Task(title, desc))
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}