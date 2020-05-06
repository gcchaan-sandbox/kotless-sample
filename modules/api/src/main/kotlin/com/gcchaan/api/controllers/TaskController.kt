package com.gcchaan.api.controllers

import com.gcchaan.api.models.Task
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class TaskController {
    val json = Json(JsonConfiguration.Stable)
    var taskList = mutableListOf(
        Task("1", "title", "desc"),
        Task("2", "title2", "desc2")
    )
    fun findAll() = json.stringify(Task.serializer().list, taskList)

    fun findById(id: String): String? {
        val task = taskList.find {
            it.id == id
        }
        return task?.let {
            json.stringify(Task.serializer(), it)
        }
    }

    fun create(title: String, description: String) {
        val task = Task("3", title, description)
        taskList.add(task)
    }

    fun update(id: String, title: String, description: String) {
        delete(id)
        taskList.add(Task(id, title, description))
    }

    fun delete(id: String) {
        taskList.removeIf {
            it.id == id
        }
    }
}
