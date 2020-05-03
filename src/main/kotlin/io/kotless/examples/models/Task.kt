package io.kotless.examples.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: String, val title: String, val description: String)

data class PostTaskParams(val title: String, val description: String)
