package com.juniar.carrierhub.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.juniar.carrierhub.Constant.CommonString.Companion.ADMIN
import com.juniar.carrierhub.Constant.CommonString.Companion.INITIALIZE
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.entity.UserEntity
import com.juniar.carrierhub.home.HomeActivity
import com.juniar.carrierhub.login.LoginActivity
import com.juniar.carrierhub.utils.SharedPreferenceUtil

/**
 * Created by Jarvis on 17/03/2018.
 */
class SplashScreenActivity : AppCompatActivity() {

    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    lateinit var daoSession: DaoSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        daoSession = (application as MainApp).daoSession
        sharedPreferenceUtil = SharedPreferenceUtil(this@SplashScreenActivity)
        if (!sharedPreferenceUtil.getBoolean(INITIALIZE)) {
            initializeDb()
        }
        Handler().postDelayed({
            if (sharedPreferenceUtil.getString(ROLE).isNotEmpty()) {
                startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }

    fun initializeDb() {
        daoSession.insert(UserEntity(null, "admin", "123456", ADMIN))
        daoSession.insert(UserEntity(null, "nico", "123456", KARYAWAN))
        sharedPreferenceUtil.setBoolean(INITIALIZE,true)
    }
}