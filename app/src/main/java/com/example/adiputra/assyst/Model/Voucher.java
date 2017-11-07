package com.example.adiputra.assyst.Model;

/**
 * Created by adiputra on 10/23/2017.
 */

public class Voucher {
    private int id, produk_id, harga;
    private String nama_voucher, kategori_voucher, deskripsi, masa_tenggang;

    public Voucher(int id, int produk_id, String nama_voucher, String kategori_voucher, String deskripsi, int harga, String masa_tenggang) {
        this.id = id;
        this.produk_id = produk_id;
        this.harga = harga;
        this.nama_voucher = nama_voucher;
        this.kategori_voucher = kategori_voucher;
        this.deskripsi = deskripsi;
        this.masa_tenggang = masa_tenggang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(int produk_id) {
        this.produk_id = produk_id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getNama_voucher() {
        return nama_voucher;
    }

    public void setNama_voucher(String nama_voucher) {
        this.nama_voucher = nama_voucher;
    }

    public String getKategori_voucher() {
        return kategori_voucher;
    }

    public void setKategori_voucher(String kategori_voucher) {
        this.kategori_voucher = kategori_voucher;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getMasa_tenggang() {
        return masa_tenggang;
    }

    public void setMasa_tenggang(String masa_tenggang) {
        this.masa_tenggang = masa_tenggang;
    }
}
