/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programdatamahasiswa;

import java.util.Locale;

/**
 *
 * @author Nazwa Kania
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private int umur;
    private double ipk;

    public Mahasiswa(String nim, String nama, int umur, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.umur = umur;
        this.ipk = ipk;
    }

    // Menggunakan Locale.US untuk memastikan titik ('.') sebagai pemisah desimal IPK,
    // sehingga tidak bentrok dengan koma (',') sebagai pemisah antar kolom.
    @Override
    public String toString() {
        return String.format(Locale.US, "%s,%s,%d,%.2f", nim, nama, umur, ipk);
    }
    
    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }
}