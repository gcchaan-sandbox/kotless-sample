package com.gcchaan.reminder

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent

data class Output(
    val message: String,
    val statusCode: Int
)

class App : RequestHandler<ScheduledEvent, Output> {
    override fun handleRequest(event: ScheduledEvent, context: Context?): Output {
        return Output(
            statusCode = 200,
            message = "Helloooo"
        )
    }
}
