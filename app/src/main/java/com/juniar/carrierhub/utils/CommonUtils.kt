package com.juniar.carrierhub.utils

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.juniar.carrierhub.Constant.CommonString.Companion.EMPTY_STRING
import com.juniar.carrierhub.R
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DateFormatSymbols
import java.util.*

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

fun Context.buildAlertDialog(title: String, message: String = EMPTY_STRING, yesButton: String = EMPTY_STRING, noButton: String = EMPTY_STRING, positiveAction: (DialogInterface) -> Unit = {}, negativeAction: (DialogInterface) -> Unit = {}): AlertDialog {
    val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)

    if (yesButton.isNotEmpty()) {
        builder.setPositiveButton(yesButton, { dialog, _ -> positiveAction.invoke(dialog) })
    }

    if (noButton.isNotEmpty()) {
        builder.setNegativeButton(noButton, { dialog, _ -> negativeAction.invoke(dialog) })
    }

    return builder.create()
}

fun changeDateFormat(input: String,oldPattern:String,newPattern:String): String {
    val oldFormat = DateTimeFormat.forPattern(oldPattern)
    val oldDateTime = oldFormat.parseDateTime(input)
    val newFormat = DateTimeFormat.forPattern(newPattern)
    val newDateTime = DateTime.parse(newFormat.print(oldDateTime), newFormat)
    return newDateTime.toString(newPattern, Locale("id", "ID", "ID"))
}

fun getMonth(month: Int): String {
    return DateFormatSymbols().months[month]
}

fun Context.getColorCompat(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Button.setAvailable(enable: Boolean, context: Context) {
    kotlin.with(this) {
        isEnabled = enable
        setBackgroundResource(if (enable) R.color.colorPrimary else R.color.coloPrimaryLight)
    }
}