package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

class ChildCreateResponse(
    @SerializedName("id") var id: String,
    @SerializedName("child_name") var childName: String,
    @SerializedName("date_of_birth") var dateOfBirth: String,
    @SerializedName("sex") var sex: String,
    @SerializedName("delayed_milestones") var delayedMilestones: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("guardian") var guardian: String
)