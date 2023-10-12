package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class SelfCareRequest(
    @SerializedName("using_toilet") var toilet:Int,
    var bathing: Int,
    var dressing: Int,
    var eating: Int,
    var child: Int,
    val total: Int,
)
