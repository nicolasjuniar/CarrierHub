package com.juniar.carrierhub.model

import com.juniar.carrierhub.Constant.CommonString.Companion.EMPTY_STRING

/**
 * Created by Jarvis on 17/03/2018.
 */
data class LoginModel(var success:Boolean=false,
                      var role:String=EMPTY_STRING)