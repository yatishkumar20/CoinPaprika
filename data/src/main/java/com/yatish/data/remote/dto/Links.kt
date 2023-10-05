package com.yatish.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Links(
    val explorer: List<Any>,
    val facebook: List<Any>,
    val medium: Any,
    val reddit: List<Any>,
    @SerializedName("source_code")
    val sourceCode: List<Any>,
    val website: List<Any>,
    val youtube: List<Any>
)