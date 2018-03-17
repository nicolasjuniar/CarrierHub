package com.juniar.carrierhub.home.karyawan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.UserEntity
import com.juniar.carrierhub.utils.buildAlertDialog
import com.juniar.carrierhub.utils.setAvailable
import com.juniar.carrierhub.utils.textToString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_edit_karyawan.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class EditKaryawanActivity : AppCompatActivity() {

    companion object {
        val ACTION = "action"
        val DELETE = "delete"
        val UPDATE = "update"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_karyawan)
        var karyawan = Gson().fromJson(intent.getStringExtra(KARYAWAN), UserEntity::class.java)
        karyawan.let {
            et_username.setText(it.username)
            et_password.setText(it.password)
        }

        Observable.combineLatest(
                RxTextView.textChanges(et_username)
                        .map { it.isNotEmpty() },
                RxTextView.textChanges(et_password)
                        .map { it.isNotEmpty() },
                BiFunction { username: Boolean, password: Boolean ->
                    username && password
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { btn_update.setAvailable(it, this@EditKaryawanActivity) }

        btn_update.setOnClickListener {
            buildAlertDialog(getString(R.string.dialog_update_karyawan_title), getString(R.string.dialog_update_karyawan_detail, karyawan.username), getString(R.string.dialog_ya), getString(R.string.dialog_tidak), {
                karyawan.username = et_username.textToString()
                karyawan.password = et_password.textToString()
                val intent = Intent()
                intent.putExtra(ACTION, UPDATE)
                intent.putExtra(KARYAWAN, Gson().toJson(karyawan))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }).show()
        }

        btn_delete.setOnClickListener {
            buildAlertDialog(getString(R.string.dialog_delete_karyawan_title), getString(R.string.dialog_delete_karyawan_detail, karyawan.username), getString(R.string.dialog_ya), getString(R.string.dialog_tidak), {
                val intent = Intent()
                intent.putExtra(ACTION, DELETE)
                intent.putExtra(KARYAWAN, Gson().toJson(karyawan))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }).show()
        }
    }
}