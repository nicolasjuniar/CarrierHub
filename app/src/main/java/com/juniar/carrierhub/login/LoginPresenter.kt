package com.juniar.carrierhub.login

import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.Constant.CommonString.Companion.USERNAME
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.utils.SharedPreferenceUtil

/**
 * Created by Jarvis on 17/03/2018.
 */
class LoginPresenter(val daoSession: DaoSession, val view: LoginView, val sharedPreferenceUtil: SharedPreferenceUtil) {

    fun login(username: String, password: String) {
        val users = daoSession.userEntityDao.loadAll()
        var check=false
        users.forEach {
            if (it.username.equals(username, true) && it.password == password) {
                check = true
                sharedPreferenceUtil.setString(ROLE, it.role)
                sharedPreferenceUtil.setString(USERNAME,it.username)
            }
        }
        view.onSuccessLogin(check)
    }
}