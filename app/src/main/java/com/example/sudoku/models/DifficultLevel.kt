package com.example.sudoku.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class DifficultLevel(val difficult: String) : Parcelable {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard")
}