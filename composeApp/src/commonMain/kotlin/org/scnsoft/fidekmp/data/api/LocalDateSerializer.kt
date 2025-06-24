package org.scnsoft.fidekmp.data.api

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
/*
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
class LocalDateSerializer : KSerializer<LocalDate> {
//    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val formatter: DateTimeFormat<LocalDate> = LocalDate.Formats.ISO
    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}

 */
//Next you need add to your data class serializer @Serializable instead @Contextual like here:
//
//@Serializable
//data class ProviderData(
//    val provider: String,
//    @Serializable(with = LocalDateSerializer::class)
//    val serviceStartDate: LocalDate,
//    @Serializable(with = LocalDateSerializer::class)
//    val serviceEndDate: LocalDate
//)

object StringOrListSerializer : JsonTransformingSerializer<List<String>>(ListSerializer(String.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonPrimitive -> JsonArray(listOf(element))
            is JsonArray -> element
            else -> throw SerializationException("Unexpected JSON type for certification")
        }
    }
}
//   @Serializable(with = StringOrListSerializer::class)
//    val certification: List<String>