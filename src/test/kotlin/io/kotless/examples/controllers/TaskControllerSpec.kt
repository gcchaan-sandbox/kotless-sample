package io.kotless.examples.controllers

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.* // ktlint-disable no-wildcard-imports
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
    describe("Task controller findAll") {
        val util = AwsDynamoDbLocalTestUtils()
        util.initSqLite()
        lateinit var ddb: AmazonDynamoDB
        beforeGroup {
            ddb = DynamoDBEmbedded.create().amazonDynamoDB()
        }
        it("dynamodb local") {
            val tableName = "kotlin_tasks"
            val hashKeyName = "item_id"
            val rcu = 100L
            val wcu = 100L
            val res = ddb.createTable(CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(listOf(
                    KeySchemaElement(hashKeyName, KeyType.HASH)
                ))
                .withAttributeDefinitions(listOf(
                    AttributeDefinition(hashKeyName, ScalarAttributeType.S)
                ))
                .withProvisionedThroughput(
                    ProvisionedThroughput(rcu, wcu)
                )
            )
            val tableDesc = res.tableDescription
            assertEquals(tableName, tableDesc.tableName)
            val tables = ddb.listTables()
            assertEquals(1, tables.tableNames.size)
        }
        afterGroup {
            ddb.shutdown()
        }
    }
})
