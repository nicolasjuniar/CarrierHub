package com.juniar.carrierhub

import android.app.Application
import com.juniar.carrierhub.Constant.CommonString.Companion.DB_NAME
import com.juniar.carrierhub.entity.DaoMaster
import com.juniar.carrierhub.entity.DaoSession
import net.danlew.android.joda.JodaTimeAndroid

/**
 * Created by Jarvis on 17/03/2018.
 */
class MainApp : Application() {

    lateinit var daoSession: DaoSession

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        daoSession = DaoMaster(DaoMaster.DevOpenHelper(this@MainApp, DB_NAME).writableDb).newSession()
    }
}