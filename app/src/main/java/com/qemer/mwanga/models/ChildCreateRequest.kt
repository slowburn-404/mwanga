package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

class ChildCreateRequest(
    @SerializedName("child_name") var childName: String,
    @SerializedName("date_of_birth") var dateOfBirth: String,
    @SerializedName("sex") var sex: String,
    @SerializedName("delayed_milestones") var delayedMilestones: String,
    @SerializedName("guardian") var guardian: String
)