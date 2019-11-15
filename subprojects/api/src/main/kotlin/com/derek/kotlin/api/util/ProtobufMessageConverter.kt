package com.derek.kotlin.api.util

import com.google.protobuf.BoolValue
import com.google.protobuf.DoubleValue
import com.google.protobuf.FloatValue
import com.google.protobuf.Int32Value
import com.google.protobuf.Int64Value
import com.google.protobuf.Message
import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import com.google.protobuf.UInt32Value
import com.google.protobuf.UInt64Value

import java.lang.reflect.InvocationTargetException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties

fun getValue(v: Any): Any {
    return when (v) {
        //is CodeValue -> v.value
        is Timestamp -> Instant.ofEpochSecond(v.seconds, v.nanos.toLong()).toEpochMilli()
        //is Time -> LocalTime.of(v.hours, v.minutes, v.seconds).format(DateTimeFormatter.ISO_LOCAL_TIME)
        //is Date -> LocalDate.of(v.years, v.months, v.days).format(DateTimeFormatter.ISO_DATE)
        is List<*> -> v.map { getValue(it!!) }
        is Int32Value -> v.value
        is UInt32Value -> v.value
        is Int64Value -> v.value
        is UInt64Value -> v.value
        is StringValue -> v.value
        is DoubleValue -> v.value
        is FloatValue -> v.value
        is BoolValue -> v.value
        is Message -> toMap(v)
        else -> v
    }
}

@Suppress("UNCHECKED_CAST")
fun <M : Message> toMap(msg: M): MutableMap<String, Any> {
    val functions = msg::class.declaredFunctions
    val map = mutableMapOf<String, Any>()
    msg::class.declaredMemberProperties.forEach { p ->
        try {
            val fieldName = p.name.substring(0, p.name.length - 1)
            val isList = p.returnType.toString().contains(Regex("List<.+>"))
            val suffix = if (isList) "List" else ""
            val getterName = "get${fieldName.capitalize()}$suffix"
            val hasName = "has${fieldName.capitalize()}$suffix"
            val getter = functions.find { func -> func.name == getterName }
            val has = functions.find { func -> func.name == hasName }
            if (getter != null) {
                val v = getter.call(msg)
                if (v != null) {
                    if (has != null) {
                        val hasResult = has.call(msg) as Boolean
                        if (hasResult) {
                            map[fieldName] = getValue(v)
                        }
                    } else {
                        map[fieldName] = getValue(v)
                    }
                }
            }
        } catch (e: NoSuchElementException) {
        } catch (e: InvocationTargetException) {
        }
    }
    return map
}
