package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

class GuardianRegistrationResponse(
    @SerializedName("id") var id: String,
    @SerializedName("children") var children: ArrayList<String> = arrayListOf(),
    @SerializedName("parent_name") var parentName: String,
    @SerializedName("national_id") var nationalId: String,
    @SerializedName("number_of_children") var numberOfChildren: String,
    @SerializedName("is_eligible") var isEligible: String,
    @SerializedName("phone_number") var phoneNumber: String,
    @SerializedName("location") var location: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String

)