package com.eyup.gurel.lib.android.base.adapter.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UUIDAdapter {
    @ToJson
    @Synchronized
    fun toJson(uuid: UUID?):String? = uuid?.toString()
    @FromJson
    @Synchronized
    fun fromJson(uuid: String?): UUID? = UUID.fromString(uuid)
}