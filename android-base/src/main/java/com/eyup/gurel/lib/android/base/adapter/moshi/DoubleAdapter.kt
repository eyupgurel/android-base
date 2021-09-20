package com.eyup.gurel.lib.android.base.adapter.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException

class DoubleAdapter {
    @ToJson
    @Synchronized
    fun doubleToJson(d: Double): String {
        return d.toString()
    }

    @FromJson
    @Synchronized
    @Throws(ParseException::class)
    fun doubleToJson(s: String): Double {
        return s.replace(',', '.').toDouble()
    }
}