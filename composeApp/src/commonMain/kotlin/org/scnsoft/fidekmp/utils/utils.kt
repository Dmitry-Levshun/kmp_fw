package org.scnsoft.fidekmp.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getTickCount() = Clock.System.now().toEpochMilliseconds()


inline fun <reified T> objectToJson(obj: T, json: Json = Json): String {
    return json.encodeToString(obj)
}

inline fun <reified T> jsonToObject(jsonStr: String, json: Json = Json): T {
    return json.decodeFromString(jsonStr)
}

fun currentUtcDateTime() = kotlinx.datetime.Clock.System.now().toLocalDateTime(TimeZone.UTC)