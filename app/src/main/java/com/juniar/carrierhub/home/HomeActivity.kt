package com.juniar.carrierhub.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.juniar.carrierhub.Constant.CommonString.Companion.ADMIN
import com.juniar.carrierhub.Constant.CommonString.Companion.KARYAWAN
import com.juniar.carrierhub.Constant.CommonString.Companion.ROLE
import com.juniar.carrierhub.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        val nav_buku=nav_view.menu.findItem(R.id.nav_buku)
        val nav_karyawan=nav_view.menu.findItem(R.id.nav_karyawan)
        val role=intent.extras.getString(ROLE)
        when(role){
            ADMIN->{
                nav_buku.isVisible=false
                nav_karyawan.isChecked=true
            }
            KARYAWAN->{
                nav_karyawan.isVisible=false
                nav_buku.isChecked=true
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
        when (item.itemId) {
            R.id.nav_karyawan -> {
                // Handle the camera action
            }
            R.id.nav_buku -> {

            }
            R.id.nav_keluar -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
