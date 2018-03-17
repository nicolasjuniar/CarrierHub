package com.juniar.carrierhub.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.home.HomeActivity
import com.juniar.carrierhub.utils.SharedPreferenceUtil
import com.juniar.carrierhub.utils.buildAlertDialog
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_login,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.action_info->{
                buildAlertDialog("Info","Aplikasi merupakan aplikasi pengelolaan buku, silahkan login dengan username nico(karyawan) atau admin(admin) dengan password 123456","ok").show()
                true
            }

            else->return super.onOptionsItemSelected(item)
        }
    }

    override fun onSuccessLogin(success: Boolean) {
        if (success) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
            showShortToast("login sukses")
        } else {
            showShortToast("username atau password salah")
        }
    }
}