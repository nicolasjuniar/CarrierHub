package com.juniar.carrierhub.home.karyawan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.adapter.GeneralRecyclerViewAdapter
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.entity.UserEntity
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.ACTION
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.DELETE
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.UPDATE
import com.juniar.carrierhub.utils.showShortToast
import kotlinx.android.synthetic.main.fragment_karyawan.*
import kotlinx.android.synthetic.main.viewholder_karyawan.view.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class KaryawanFragment : Fragment(), KaryawanView {

    lateinit var daoSession: DaoSession
    var userList = mutableListOf<UserEntity>()

    companion object {
        val KARYAWAN_CODE = 1
        fun newInstance(): KaryawanFragment {
            return KaryawanFragment()
        }
    }

    val userAdapter by lazy {
        GeneralRecyclerViewAdapter(R.layout.viewholder_karyawan, userList,
                { user, _, _ ->
                    val intent = Intent(activity, EditKaryawanActivity::class.java)
                    intent.putExtra(KARYAWAN, Gson().toJson(user))
                    startActivityForResult(intent, KARYAWAN_CODE)
                },
                { user, view ->
                    with(user) {
                        view.tv_id.text = user.id.toString()
                        view.tv_nama.text = user.username
                    }
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_karyawan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daoSession = (activity.application as MainApp).daoSession
        setKaryawan()
        with(rv_karyawan) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        fab.setOnClickListener {
            if (userList.size < 5) {
                val dialog = KaryawanDialog()
                dialog.setTargetFragment(this, 1)
                dialog.show(activity.supportFragmentManager, KARYAWAN)
            } else {
                activity.showShortToast(getString(R.string.maximal_karyawan_alert))
            }
        }
    }

    fun setKaryawan() {
        userList.clear()
        daoSession.userEntityDao.loadAll().forEach {
            if (it.role.equals(KARYAWAN, true)) {
                userList.add(it)
            }
        }
    }

    override fun onAddKaryawan(user: UserEntity) {
        daoSession.insert(user)
        userList.add(user)
        userAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KARYAWAN_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val karyawan = Gson().fromJson(it.getStringExtra(KARYAWAN), UserEntity::class.java)
                    when (it.getStringExtra(ACTION)) {
                        UPDATE -> daoSession.userEntityDao.update(karyawan)
                        DELETE -> daoSession.userEntityDao.delete(karyawan)
                    }
                    setKaryawan()
                    userAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}