package com.qemer.mwanga.models

import java.io.Serializable


data class ParentData(
    val parentName: String,
    val parentId: String,
    val parentPhoneNo: String,
    val geolocation: String,
    val totalChildren: String
) : Serializable