package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class GuardianRegistrationRequest(
    @SerializedName("parent_name") var parentName: String,
    @SerializedName("national_id") var nationalId: String,
    @SerializedName("phone_number") var phoneNumber: String,
    @SerializedName("number_of_children") var numberOfChildren: String,
    @SerializedName("is_eligible") var isEligible: String,
    @SerializedName("location") var location: String
)