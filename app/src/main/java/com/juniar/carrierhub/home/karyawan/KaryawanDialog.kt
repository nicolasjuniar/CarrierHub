package com.juniar.carrierhub.home.karyawan

import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.UserEntity
import com.juniar.carrierhub.utils.textToString
import kotlinx.android.synthetic.main.dialog_karyawan.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class KaryawanDialog:DialogFragment(){
    lateinit var calback:KaryawanView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater?.inflate(R.layout.dialog_karyawan, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.let {
            it.window.requestFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(false)
        }

        calback=targetFragment as KaryawanView
        btn_add.setOnClickListener {
            calback.onAddKaryawan(UserEntity(null,et_username.textToString(),et_password.textToString(),KARYAWAN))
            dismiss()
        }

        btn_cancel.setOnClickListener {
            dismiss()
        }
    }
}
