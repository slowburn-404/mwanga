package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class GetGuardianDetails(
    @SerializedName("id") var id: String,
    @SerializedName("children") var children: ArrayList<Children> = arrayListOf(),
    @SerializedName("parent_name") var parentName: String,
    @SerializedName("national_id") var nationalId: String,
    @SerializedName("number_of_children") var numberOfChildren: String,
    @SerializedName("is_eligible") var isEligible: Boolean,
    @SerializedName("phone_number") var phoneNumber: String,
    @SerializedName("location") var location: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String
)

data class Children(
    @SerializedName("id") var id: String,
    @SerializedName("child_name") var childName: String,
    @SerializedName("date_of_birth") var dateOfBirth: String,
    @SerializedName("sex") var sex: String,
    @SerializedName("delayed_milestones") var delayedMilestones: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("guardian") var guardian: String
)