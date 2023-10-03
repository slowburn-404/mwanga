package com.qemer.mwanga.dashboard.registration

import android.text.Editable
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import java.io.Serializable

data class NumberOfChildrenModel(
    val childNumber: String,
    var childName: String,
    var DOB: String,
    var gender: String,
    var delayedMilestones: String
) : Serializable


