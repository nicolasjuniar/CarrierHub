package com.juniar.carrierhub.home.karyawan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.UserEntity
import com.juniar.carrierhub.utils.buildAlertDialog
import com.juniar.carrierhub.utils.textToString
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

        btn_update.setOnClickListener {
            buildAlertDialog("Update Karyawan", "Apakah anda yakin mengupdate karyawan ${karyawan.username}?", "ya", "tidak", {
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
            buildAlertDialog("Delete Karyawan", "Apakah anda yakin menghapus karyawan ${karyawan.username}?", "ya", "tidak", {
                val intent = Intent()
                intent.putExtra(ACTION, DELETE)
                intent.putExtra(KARYAWAN, Gson().toJson(karyawan))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }).show()
        }
    }
}