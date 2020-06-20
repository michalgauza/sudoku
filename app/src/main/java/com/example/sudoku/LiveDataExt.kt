package com.example.sudoku

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.mutation(action: (MutableLiveData<T>) -> Unit) {
    action(this)
    this.value = this.value
}