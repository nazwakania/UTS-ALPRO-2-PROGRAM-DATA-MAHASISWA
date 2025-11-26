/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programdatamahasiswa;
import java.io.*;
import java.util.*;

/**
 *
 * @author Nazwa Kania
 */

public class Main {

    private static final String FILENAME = "mahasiswa.txt"; 
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            System.out.print("Pilih menu (1-4): ");
            try {
                String input = scanner.nextLine(); 
                if (input.isEmpty()) {
                    choice = 0;
                    continue;
                }
                choice = Integer.parseInt(input);
                
                switch (choice) {
                    case 1:
                        inputAndSave(); // Menu 1: Input dan Simpan Data
                        break;
                    case 2:
                        readAndDisplay(); // Menu 2: Tampilkan Data dari File
                        break;
                    case 3:
                        recursiveSearch(); // Menu 3: Pencarian Rekursif
                        break;
                    case 4:
                        System.out.println("Terima kasih, program berakhir.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka (1-4).");
                choice = 0;
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan tak terduga: " + e.getMessage());
                choice = 0;
            }
            System.out.println();
        } while (choice != 4);
    }

    // Method untuk menampilkan menu utama
    private static void displayMenu() {
        System.out.println("=== PROGRAM DATA MAHASISWA ===");
        System.out.println("1. Input dan Simpan Data ke File");
        System.out.println("2. Tampilkan Data dari File");
        System.out.println("3. Pencarian Rekursif (method recursion)");
        System.out.println("4. Keluar");
        System.out.println("------------------------------------");
    }

    // Method 1: Input dan Simpan Data ke File
    private static void inputAndSave() {
        System.out.println("\n--- Input Data Mahasiswa ---");

        try {
            System.out.print("NIM: ");
            String nim = scanner.nextLine();
            System.out.print("Nama: ");
            String nama = scanner.nextLine();
            System.out.print("Umur: ");
            int umur = Integer.parseInt(scanner.nextLine());
            System.out.print("IPK: ");
            double ipk = Double.parseDouble(scanner.nextLine());

            Mahasiswa mhs = new Mahasiswa(nim, nama, umur, ipk);

            // Menggunakan try-with-resources
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILENAME, true))) {
                pw.println(mhs.toString()); 
                pw.flush();
                System.out.println("Data berhasil disimpan ke " + FILENAME);
            } catch (IOException e) {
                System.err.println("Gagal menulis ke file: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.err.println("Input Umur atau IPK tidak valid (harus angka).");
        } catch (Exception e) {
            System.err.println("Error saat menyimpan data: " + e.getMessage());
        }
    }

    // Method 2: Tampilkan Data dari File (Output Rapi)
    private static void readAndDisplay() {
        System.out.println("\n--- Data Mahasiswa dari File ---");
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            int count = 0;
            
            // Header Tabel (Rapi)
            System.out.printf("| %-12s | %-30s | %-5s | %-5s |\n", "NIM", "NAMA", "UMUR", "IPK");
            System.out.println("-----------------------------------------------------------------");
            
            while ((line = br.readLine()) != null) {
                
                line = line.trim(); 
                if (line.isEmpty()) {
                    continue; 
                }

                String[] data = line.split(",");
                
                // Cek array length harus 4
                if (data.length == 4) { 
                    // Tampilkan data ke console dengan format yang rapi
                    System.out.printf("| %-12s | %-30s | %-5s | %-5s |\n", 
                                      data[0], data[1], data[2], data[3]);
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("File kosong atau tidak ada data.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + FILENAME + " belum ditemukan. Silakan input data terlebih dahulu.");
        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saat menampilkan data: " + e.getMessage());
        }
    }

    // Method pembantu untuk memuat semua data dari file ke dalam List
    private static List<Mahasiswa> loadDataFromFile() {
        List<Mahasiswa> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                if (line.isEmpty()) {
                    continue; 
                }
                String[] data = line.split(",");
                
                // Memastikan data valid (4 kolom)
                if (data.length == 4) {
                    Mahasiswa mhs = new Mahasiswa(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]));
                    list.add(mhs);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Mengabaikan error
        }
        return list;
    }

    // Method 3: Pencarian Rekursif
    private static void recursiveSearch() {
        System.out.println("\n--- Pencarian Data (Rekursif) ---");
        List<Mahasiswa> listMahasiswa = loadDataFromFile();

        if (listMahasiswa.isEmpty()) {
            System.out.println("Tidak ada data untuk dicari. Silakan input data terlebih dahulu.");
            return;
        }

        System.out.print("Masukkan NIM atau Nama yang dicari: ");
        String keyword = scanner.nextLine();
        
        int index = search(listMahasiswa, keyword, 0); 
        
        if (index != -1) {
            Mahasiswa found = listMahasiswa.get(index);
            System.out.println("==============================================");
            System.out.println("Data Ditemukan!");
            System.out.println("  NIM : " + found.getNim());
            System.out.println("  Nama: " + found.getNama());
            System.out.println("==============================================");
        } else {
            System.out.println("Data dengan keyword '" + keyword + "' tidak ditemukan.");
        }
    }
    
    /**
     * Method Rekursif untuk mencari Mahasiswa berdasarkan NIM atau Nama.
     */
    private static int search(List<Mahasiswa> list, String keyword, int index) {
        if (index >= list.size()) {
            return -1;
        }
        
        if (list.get(index).getNim().equalsIgnoreCase(keyword) || list.get(index).getNama().equalsIgnoreCase(keyword)) {
            return index;
        }
        
        return search(list, keyword, index + 1);
    }
}