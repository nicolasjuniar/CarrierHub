package com.juniar.carrierhub.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Jarvis on 17/03/2018.
 */
@Entity(nameInDb = "book")
public class BarangEntity {
    @Id(autoincrement = true)
    Long id;
    @Property(nameInDb = "name")
    String nama;
    @Property(nameInDb = "jumlah")
    int jumlah;
    @Property(nameInDb = "pemasok")
    String pemasok;
    @Property(nameInDb = "tanggal")
    String tanggal;

    @Generated(hash = 678444320)
    public BarangEntity(Long id, String nama, int jumlah, String pemasok, String tanggal) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.pemasok = pemasok;
        this.tanggal = tanggal;
    }

    @Generated(hash = 1762989036)
    public BarangEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getPemasok() {
        return this.pemasok;
    }

    public void setPemasok(String pemasok) {
        this.pemasok = pemasok;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
