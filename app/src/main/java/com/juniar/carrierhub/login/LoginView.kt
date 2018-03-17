package com.juniar.carrierhub.login

import com.juniar.carrierhub.model.LoginModel

/**
 * Created by Jarvis on 17/03/2018.
 */
interface LoginView{
    fun onGetUser(model:LoginModel)
}