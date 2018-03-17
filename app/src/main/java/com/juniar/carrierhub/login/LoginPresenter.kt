package com.juniar.carrierhub.login

import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.model.LoginModel
import com.juniar.carrierhub.utils.SharedPreferenceUtil

/**
 * Created by Jarvis on 17/03/2018.
 */
class LoginPresenter(val daoSession: DaoSession, val view: LoginView, val sharedPreferenceUtil: SharedPreferenceUtil) {

    fun login(username: String, password: String) {
        val users = daoSession.userEntityDao.loadAll()
        var model = LoginModel()
        users.forEach {
            if (it.username.equals(username, true) && it.password == password) {
                model.success = true
                model.role = it.role
                sharedPreferenceUtil.setString(ROLE, it.role)
            }
        }
        view.onGetUser(model)
    }
}