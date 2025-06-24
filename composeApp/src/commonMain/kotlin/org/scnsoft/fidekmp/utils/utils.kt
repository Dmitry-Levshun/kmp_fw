package org.scnsoft.fidekmp.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.StringResource
import kotlin.math.pow
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
fun mSecToLocalDateTime(millis: Long) = Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC)
fun LocalDateTime.toSimpleString() = this.toString()
fun LocalDateTime.toSimpleMonthString() = this.month.toString()
fun LocalDate.toSimpleMonthString() = this.month.toString()
fun LocalDate?.toLocalDateTime() = LocalDateTime(this ?: LocalDate(1, 1, 1), LocalTime(0, 0))

fun currencyToSymbol(c: String): String =
    when (c) {
        "USD" -> "$"
        "EUR"  -> "€"
        else -> "€"
    }
fun symbolToCurrency(c: String): String =
    when (c) {
        "$" -> "USD"
        "€" -> "EUR"
        else -> "EUR"
    }

fun Double.toFixed(decimals: Int): String {
    val factor = 10.0.pow(decimals)
    val rounded = kotlin.math.round(this * factor) / factor
    return rounded.toString()
}

fun volumeDoubleToString(vlm: Double?) =
    if (vlm == null)  ""
    else if (vlm > 1f) vlm.toFixed(1) + " l"
    else (vlm * 1000).toInt().toString() + " ml"
