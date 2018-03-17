package com.juniar.carrierhub.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.juniar.carrierhub.Constant.CommonString.Companion.ADMIN
import com.juniar.carrierhub.Constant.CommonString.Companion.EMPTY_STRING
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.R
import com.juniar.carrierhub.home.karyawan.KaryawanFragment
import com.juniar.carrierhub.login.LoginActivity
import com.juniar.carrierhub.utils.SharedPreferenceUtil
import com.juniar.carrierhub.utils.buildAlertDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    lateinit var fragmentManager:FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        fragmentManager=supportFragmentManager
        sharedPreferenceUtil = SharedPreferenceUtil(this@HomeActivity)
        val nav_buku = nav_view.menu.findItem(R.id.nav_buku)
        val nav_karyawan = nav_view.menu.findItem(R.id.nav_karyawan)
        val role = sharedPreferenceUtil.getString(ROLE)
        when (role) {
            ADMIN -> {
                supportActionBar?.title="Kelola Karyawan"
                nav_buku.isVisible = false
                nav_karyawan.isChecked = true
                fragmentManager.beginTransaction().replace(R.id.container_body,KaryawanFragment.newInstance()).commit()
            }
            KARYAWAN -> {
                supportActionBar?.title="Kelola Buku"
                nav_karyawan.isVisible = false
                nav_buku.isChecked = true
            }
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_karyawan -> {
                fragment = KaryawanFragment.newInstance()
            }
            R.id.nav_buku -> {

            }
            R.id.nav_keluar -> {
                buildAlertDialog("Keluar", "Apakah anda ingin keluar?", "ya", "tidak", {
                    sharedPreferenceUtil.setString(ROLE,EMPTY_STRING)
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                    finish()
                }).show()
            }
        }

        fragment?.let {
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment).commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
