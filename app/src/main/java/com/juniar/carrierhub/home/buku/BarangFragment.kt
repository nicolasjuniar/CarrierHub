package com.juniar.carrierhub.home.buku

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.juniar.carrierhub.MainApp
import com.juniar.carrierhub.R
import com.juniar.carrierhub.adapter.GeneralRecyclerViewAdapter
import com.juniar.carrierhub.entity.BarangEntity
import com.juniar.carrierhub.entity.DaoSession
import com.juniar.carrierhub.home.buku.PengelolaanBarangActivity.Companion.ADD
import com.juniar.carrierhub.home.buku.PengelolaanBarangActivity.Companion.BARANG
import com.juniar.carrierhub.home.buku.PengelolaanBarangActivity.Companion.EDIT
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.ACTION
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.DELETE
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.UPDATE
import kotlinx.android.synthetic.main.fragment_barang.*
import kotlinx.android.synthetic.main.viewholder_barang.view.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class BarangFragment : Fragment() {

    lateinit var daoSession: DaoSession
    var listBarang = mutableListOf<BarangEntity>()

    companion object {
        val BARANG_CODE = 2
        fun newInstance(): BarangFragment {
            return BarangFragment()
        }
    }

    val barangAdapter by lazy {
        GeneralRecyclerViewAdapter(R.layout.viewholder_barang, listBarang,
                { barang, _, _ ->
                    val intent = Intent(activity, PengelolaanBarangActivity::class.java)
                    intent.putExtra(BARANG, Gson().toJson(barang))
                    intent.putExtra(ACTION, EDIT)
                    startActivityForResult(intent, BARANG_CODE)
                },
                { barang, view ->
                    with(barang) {
                        view.tv_nama.text = barang.nama
                        view.tv_tanggal.text = barang.tanggal
                        view.tv_pemasok.text = "Pemasok: ${barang.pemasok}"
                        view.tv_stok.text = "Stok: ${barang.jumlah}"
                    }
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_barang, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daoSession = (activity.application as MainApp).daoSession
        setBarang()
        with(rv_barang) {
            adapter = barangAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        fab.setOnClickListener {
            val intent = Intent(activity, PengelolaanBarangActivity::class.java)
            intent.putExtra(ACTION, ADD)
            startActivityForResult(intent, BARANG_CODE)
        }
    }

    fun setBarang() {
        listBarang.clear()
        listBarang.addAll(daoSession.barangEntityDao.loadAll())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BARANG_CODE) {
            data?.let {
                val barang = Gson().fromJson(it.getStringExtra(BARANG), BarangEntity::class.java)
                when (it.getStringExtra(ACTION)) {
                    ADD -> daoSession.insert(barang)
                    DELETE -> daoSession.barangEntityDao.delete(barang)
                    UPDATE->daoSession.barangEntityDao.update(barang)
                }
                setBarang()
                barangAdapter.notifyDataSetChanged()
            }
        }
    }
}
