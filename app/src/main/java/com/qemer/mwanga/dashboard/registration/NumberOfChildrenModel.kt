package com.qemer.mwanga.dashboard.registration

import android.text.Editable
import android.widget.CheckBox

data class NumberOfChildrenModel(
    val childNumber: String,
    val childName: Editable,
    val DOB : Editable,
    val gender: Boolean,
    val delayedmMlestones: Editable
)


