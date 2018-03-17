package com.juniar.carrierhub.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.home.HomeActivity
import com.juniar.carrierhub.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
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

        Observable.combineLatest(
                RxTextView.textChanges(et_username)
                        .map { it.isNotEmpty() },
                RxTextView.textChanges(et_password)
                        .map { it.isNotEmpty() },
                BiFunction { username: Boolean, password: Boolean ->
                    username && password
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { btn_login.setAvailable(it, this@LoginActivity) }

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
                buildAlertDialog(getString(R.string.dialog_info_title),getString(R.string.dialog_info_detail),getString(R.string.dialog_ok)).show()
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