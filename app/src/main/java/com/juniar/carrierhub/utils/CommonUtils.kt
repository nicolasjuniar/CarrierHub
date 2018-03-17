package com.juniar.carrierhub.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast

/**
 * Created by Jarvis on 17/03/2018.
 */

fun Context.showShortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun EditText.textToString(): String {
    return this.text.toString()
}