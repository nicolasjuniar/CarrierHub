package com.juniar.carrierhub.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.home.HomeActivity
import com.juniar.carrierhub.model.LoginModel
import com.juniar.carrierhub.utils.SharedPreferenceUtil
import com.juniar.carrierhub.utils.showShortToast
import com.juniar.carrierhub.utils.textToString
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var presenter: LoginPresenter
    lateinit var daoSession: DaoSession
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        daoSession = (application as MainApp).daoSession
        sharedPreferenceUtil = SharedPreferenceUtil(this@LoginActivity)
        presenter = LoginPresenter(daoSession, this, sharedPreferenceUtil)
        btn_login.setOnClickListener {
            presenter.login(et_username.textToString(), et_password.textToString())
        }
    }

    override fun onGetUser(model: LoginModel) {
        if (model.success) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra(ROLE, model.role)
            startActivity(intent)
            finish()
            showShortToast("login sukses")
        } else {
            showShortToast("username atau password salah")
        }
    }
}