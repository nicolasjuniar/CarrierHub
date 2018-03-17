package com.juniar.carrierhub.home.buku

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import com.juniar.carrierhub.R
import com.juniar.carrierhub.entity.BarangEntity
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.ACTION
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.DELETE
import com.juniar.carrierhub.home.karyawan.EditKaryawanActivity.Companion.UPDATE
import com.juniar.carrierhub.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.activity_pengelolaan_barang.*
import java.util.*

/**
 * Created by Jarvis on 17/03/2018.
 */
class PengelolaanBarangActivity : AppCompatActivity() {

    var barangIntent = Intent()

    companion object {
        val BARANG = "barang"
        val ADD = "add"
        val EDIT = "edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengelolaan_barang)
        val action = intent.getStringExtra(ACTION)
        var calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(this@PengelolaanBarangActivity, DatePickerDialog.OnDateSetListener
        { _, year, month, day ->
            et_tanggal.setText(changeDateFormat("$day ${getMonth(month)} $year", "d MMMM yyyy", "d MMMM yyyy"))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))


        Observable.combineLatest(
                RxTextView.textChanges(et_nama)
                        .map { it.isNotEmpty() },
                RxTextView.textChanges(et_pemasok)
                        .map { it.isNotEmpty() },
                RxTextView.textChanges(et_stok)
                        .map { it.isNotEmpty() },
                RxTextView.textChanges(et_tanggal)
                        .map { it.isNotEmpty() },
                Function4 { nama: Boolean, pemasok: Boolean, stok: Boolean, tanggal: Boolean ->
                    nama && pemasok && stok && tanggal
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    btn_add.setAvailable(it, this@PengelolaanBarangActivity)
                    btn_update.setAvailable(it, this@PengelolaanBarangActivity)
                }

        et_tanggal.setOnClickListener {
            datePicker.show()
        }

        setLayout(action)

        if (action == EDIT) {
            var barang = Gson().fromJson(intent.getStringExtra(BARANG), BarangEntity::class.java)
            et_nama.setText(barang.nama)
            et_pemasok.setText(barang.pemasok)
            et_stok.setText(barang.jumlah.toString())
            et_tanggal.setText(barang.tanggal)

            btn_update.setOnClickListener {
                buildAlertDialog(getString(R.string.dialog_update_barang_title), getString(R.string.dialog_update_barang_detail, barang.nama), getString(R.string.dialog_ya), getString(R.string.dialog_tidak), {
                    barangIntent.putExtra(ACTION, UPDATE)
                    with(barang)
                    {
                        nama = et_nama.textToString()
                        pemasok = et_pemasok.textToString()
                        tanggal = et_tanggal.textToString()
                        jumlah = et_stok.textToString().toInt()
                    }
                    editBarang(barang)
                }).show()
            }

            btn_delete.setOnClickListener {
                buildAlertDialog(getString(R.string.dialog_delete_barang_title), getString(R.string.dialog_delete_barang_detail, barang.nama), getString(R.string.dialog_ya), getString(R.string.dialog_tidak), {
                    barangIntent.putExtra(ACTION, DELETE)
                    editBarang(barang)
                }).show()
            }
        } else {

        }

        btn_add.setOnClickListener {
            val barang = BarangEntity(null, et_nama.textToString(), et_stok.textToString().toInt(), et_pemasok.textToString(), et_tanggal.textToString())
            val intent = Intent()
            intent.putExtra(ACTION, action)
            intent.putExtra(BARANG, Gson().toJson(barang))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun editBarang(barangEntity: BarangEntity) {
        barangIntent.putExtra(BARANG, Gson().toJson(barangEntity))
        setResult(Activity.RESULT_OK, barangIntent)
        finish()
    }

    fun setLayout(action: String) {
        when (action) {
            ADD -> {
                btn_add.show()
                btn_delete.hide()
                btn_update.hide()
            }
            EDIT -> {
                btn_add.hide()
                btn_delete.show()
                btn_update.show()
            }
        }
    }
}