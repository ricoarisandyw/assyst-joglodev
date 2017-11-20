package com.example.adiputra.assyst.Model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by adiputra on 10/23/2017.
 */

public class Voucher implements Comparable{
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

    @Override
    public int compareTo(@NonNull Object o) {
        int compareage=((Voucher)o).getHarga();
        /* For Ascending order*/
        return this.harga-compareage;
    }

    public static Comparator<Voucher> VoucherName = new Comparator<Voucher>() {

        public int compare(Voucher s1, Voucher s2) {
            String VoucherName1 = s1.getNama_voucher().toUpperCase();
            String VoucherName2 = s2.getNama_voucher().toUpperCase();

            //ascending order
            return VoucherName1.compareTo(VoucherName2);
        }};

    public static Comparator<Voucher> VoucherPrice = new Comparator<Voucher>() {

            public int compare(Voucher s1, Voucher s2) {

                int rollno1 = s1.getHarga();
                int rollno2 = s2.getHarga();

	            /*For ascending order*/
                return rollno1-rollno2;
        }};
}