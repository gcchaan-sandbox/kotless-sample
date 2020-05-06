package com.gcchaan.reminder

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent
import kotlin.test.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AppSpec : Spek({
    describe("Handler") {
        it("return output") {
            val app = App()
            val acutual = app.handleRequest(ScheduledEvent(), null)
            val expected = Output(
                statusCode = 200,
                message = "Helloooo"
            )
            assertEquals(expected, acutual)
        }
    }
})
