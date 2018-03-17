package com.juniar.carrierhub.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.juniar.carrierhub.Constant.CommonString.Companion.USERNAME
import com.juniar.carrierhub.R
import com.juniar.carrierhub.home.buku.BarangFragment
import com.juniar.carrierhub.home.karyawan.KaryawanFragment
import com.juniar.carrierhub.login.LoginActivity
import com.juniar.carrierhub.utils.SharedPreferenceUtil
import com.juniar.carrierhub.utils.buildAlertDialog
import com.juniar.carrierhub.utils.showShortToast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    lateinit var fragmentManager: FragmentManager
    var exit=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        fragmentManager = supportFragmentManager
        sharedPreferenceUtil = SharedPreferenceUtil(this@HomeActivity)
        val navView = nav_view.getHeaderView(0)
        navView.tv_username.text=sharedPreferenceUtil.getString(USERNAME)
        val navBarang = nav_view.menu.findItem(R.id.nav_barang)
        val navKaryawan = nav_view.menu.findItem(R.id.nav_karyawan)
        val role = sharedPreferenceUtil.getString(ROLE)
        when (role) {
            ADMIN -> {
                supportActionBar?.title = getString(R.string.kelola_karyawan)
                navBarang.isVisible = false
                navKaryawan.isChecked = true
                fragmentManager.beginTransaction().replace(R.id.container_body, KaryawanFragment.newInstance()).commit()
            }
            KARYAWAN -> {
                supportActionBar?.title = getString(R.string.kelola_barang)
                navKaryawan.isVisible = false
                navBarang.isChecked = true
                fragmentManager.beginTransaction().replace(R.id.container_body, BarangFragment()).commit()
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
            if (exit) {
                finish()
            } else {
                this.showShortToast(getString(R.string.exit_text))
                exit = true
                Handler().postDelayed({ exit = false }, (3 * 1000).toLong())
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_karyawan -> {
                fragment = KaryawanFragment.newInstance()
            }
            R.id.nav_barang -> {
                fragment = BarangFragment.newInstance()
            }
            R.id.nav_keluar -> {
                buildAlertDialog(getString(R.string.dialog_keluar_title), getString(R.string.dialog_keluar_detail), getString(R.string.dialog_ya), getString(R.string.dialog_tidak), {
                    sharedPreferenceUtil.setString(ROLE, EMPTY_STRING)
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
