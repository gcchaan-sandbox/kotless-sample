package io.kotless.examples.controllers

import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TaskControllerSpec : Spek({
    describe("Task controller findAll") {
        it("return all tasks") {
            val tc = TaskController()
            assertEquals(tc.findAll(), "[{\"id\":\"1\",\"title\":\"title\",\"description\":\"desc\"},{\"id\":\"2\",\"title\":\"title2\",\"description\":\"desc2\"}]")
        }
    }
})
