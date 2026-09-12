import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ArrayList<Product> daftarProduk = new ArrayList<>();
    private static ArrayList<transaction> daftarTransaksi = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int counterTransaksi = 1;

    public static void main(String[] args) {
        inisialisasiDataContoh();

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenuUtama();
            int pilihan = bacaPilihanMenu();

            switch (pilihan) {
                case 1:
                    menuManajemenProduk();
                    break;
                case 2:
                    prosesTransaksiBaru();
                    break;
                case 3:
                    menuLaporan();
                    break;
                case 0:
                    berjalan = false;
                    System.out.println("Terima kasih telah menggunakan Sistem Stock.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.\n");
            }
        }
        scanner.close();
    }

    private static void inisialisasiDataContoh() {
        daftarProduk.add(new Product("P001", "Beras 5kg", "Makanan", 65000, 20));
        daftarProduk.add(new Product("P002", "Minyak Goreng 2L", "Makanan", 32000, 15));
        daftarProduk.add(new Product("P003", "Air Mineral 600ml", "Minuman", 3000, 100));
        daftarProduk.add(new Product("P004", "Sabun Cuci Piring", "Alat Kebersihan", 12500, 8));
        daftarProduk.add(new Product("P005", "Kabel Charger USB-C", "Elektronik", 45000, 5));
    }


    private static void tampilkanMenuUtama() {
        System.out.println("\n===== SISTEM MANAJEMEN TOKO RETAIL (TR) =====");
        System.out.println("1. Manajemen Produk");
        System.out.println("2. Pemrosesan Transaksi");
        System.out.println("3. Laporan & Analitik");
        System.out.println("0. Keluar");
        System.out.print("Masukkan pilihan Anda: ");
    }


    private static int bacaPilihanMenu() {
        int pilihan = -1;
        try {
            pilihan = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Harap masukkan angka.");
        } finally {
            scanner.nextLine(); 
        }
        return pilihan;
    }


    private static void menuManajemenProduk() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- MANAJEMEN PRODUK ---");
            System.out.println("1. Tampilkan Semua Produk");
            System.out.println("2. Tambah Produk Baru");
            System.out.println("3. Cari Produk (Nama/Kategori)");
            System.out.println("4. Perbarui Stok Produk");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilihan: ");
            int pilihan = bacaPilihanMenu();

            switch (pilihan) {
                case 1:
                    tampilkanSemuaProduk();
                    break;
                case 2:
                    tambahProdukBaru();
                    break;
                case 3:
                    cariProduk();
                    break;
                case 4:
                    perbaruiStokProduk();
                    break;
                case 0:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tampilkanSemuaProduk() {
        System.out.println("\nID     | Nama                 | Kategori     | Harga        | Stok");
        System.out.println("--------------------------------------------------------------------");
        if (daftarProduk.isEmpty()) {
            System.out.println("Belum ada produk yang terdaftar.");
            return;
        }
        for (Product p : daftarProduk) {
            System.out.println(p);
        }
    }

    private static void tambahProdukBaru() {
        try {
            System.out.print("Masukkan ID produk: ");
            String id = scanner.nextLine();

            System.out.print("Masukkan nama produk: ");
            String nama = scanner.nextLine();

            System.out.print("Masukkan kategori produk: ");
            String kategori = scanner.nextLine();

            System.out.print("Masukkan harga produk: ");
            double harga = Double.parseDouble(scanner.nextLine());

            System.out.print("Masukkan stok awal: ");
            int stok = Integer.parseInt(scanner.nextLine());

            daftarProduk.add(new Product(id, nama, kategori, harga, stok));
            System.out.println("Produk berhasil ditambahkan.");
        } catch (NumberFormatException e) {
            System.out.println("Harga dan stok harus berupa angka. Produk gagal ditambahkan.");
        }
    }

    private static void cariProduk() {
        System.out.print("Masukkan kata kunci nama atau kategori: ");
        String kataKunci = scanner.nextLine().toLowerCase();

        List<Product> hasil = new ArrayList<>();
        for (Product p : daftarProduk) {
            if (p.getName().toLowerCase().contains(kataKunci)
                    || p.getCategory().toLowerCase().contains(kataKunci)) {
                hasil.add(p);
            }
        }

        if (hasil.isEmpty()) {
            System.out.println("Tidak ada produk yang cocok dengan kata kunci tersebut.");
        } else {
            System.out.println("Hasil pencarian:");
            for (Product p : hasil) {
                System.out.println(p);
            }
        }
    }

    private static void perbaruiStokProduk() {
        System.out.print("Masukkan ID produk yang akan diperbarui stoknya: ");
        String id = scanner.nextLine();
        try {
            Product produk = cariProdukById(id);
            System.out.print("Masukkan jumlah stok baru: ");
            int stokBaru = Integer.parseInt(scanner.nextLine());
            produk.setStock(stokBaru);
            System.out.println("Stok produk " + produk.getName() + " berhasil diperbarui menjadi " + stokBaru + ".");
        } catch (NotFoundException e) {
            System.out.println("Gagal memperbarui stok: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Jumlah stok harus berupa angka.");
        }
    }

    private static Product cariProdukById(String id) throws NotFoundException {
        for (Product p : daftarProduk) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new NotFoundException("Produk dengan ID " + id + " tidak ditemukan.");
    }


    private static void prosesTransaksiBaru() {
        String idTransaksi = "TRX" + String.format("%03d", counterTransaksi);
        transaction transaksi = new transaction(idTransaksi, "");

        boolean tambahLagi = true;
        while (tambahLagi) {
            System.out.print("Masukkan ID produk (atau ketik 'selesai' untuk mengakhiri): ");
            String idProduk = scanner.nextLine();

            if (idProduk.equalsIgnoreCase("selesai")) {
                tambahLagi = false;
                continue;
            }

            try {
                Product produk = cariProdukById(idProduk);
                System.out.print("Masukkan jumlah beli: ");
                int jumlah = Integer.parseInt(scanner.nextLine());

                if (jumlah <= 0) {
                    System.out.println("Jumlah beli harus lebih dari 0.");
                    continue;
                }

                produk.kurangiStok(jumlah); // melempar IllegalArgumentException jika stok tidak cukup
                transaksi.tambahItem(new transactionitem(produk, jumlah));
                System.out.println(produk.getName() + " x" + jumlah + " ditambahkan ke keranjang.");

            } catch (NotFoundException e) {
                System.out.println("Gagal menambahkan item: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Jumlah beli harus berupa angka.");
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal menambahkan item: " + e.getMessage());
            }
        }

        if (transaksi.getItems().isEmpty()) {
            System.out.println("Transaksi dibatalkan karena tidak ada item yang ditambahkan.");
            return;
        }

        String metodeBayar = pilihMetodePembayaran();
        transaksi.setPaymentMethod(metodeBayar);

        daftarTransaksi.add(transaksi);
        counterTransaksi++;

        transaksi.cetakStruk();
    }

    private static String pilihMetodePembayaran() {
        while (true) {
            System.out.println("Pilih metode pembayaran:");
            System.out.println("1. Tunai");
            System.out.println("2. Transfer");
            System.out.print("Pilihan: ");
            int pilihan = bacaPilihanMenu();

            switch (pilihan) {
                case 1:
                    return "TUNAI";
                case 2:
                    return "TRANSFER";
                default:
                    System.out.println("Pilihan tidak valid, silakan ulangi.");
            }
        }
    }

    private static void menuLaporan() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- LAPORAN & ANALITIK ---");
            System.out.println("1. Ringkasan Penjualan Harian");
            System.out.println("2. Produk dengan Stok Menipis (<10 unit)");
            System.out.println("3. 3 Produk Terlaris");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilihan: ");
            int pilihan = bacaPilihanMenu();

            switch (pilihan) {
                case 1:
                    tampilkanRingkasanHarian();
                    break;
                case 2:
                    tampilkanStokMenipis();
                    break;
                case 3:
                    tampilkanProdukTerlaris();
                    break;
                case 0:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tampilkanRingkasanHarian() {
        double totalPendapatan = 0;
        for (transaction t : daftarTransaksi) {
            totalPendapatan += t.hitungTotalAkhir();
        }
        System.out.println("\n--- RINGKASAN PENJUALAN HARIAN ---");
        System.out.println("Jumlah Transaksi : " + daftarTransaksi.size());
        System.out.printf("Total Pendapatan  : Rp%,.2f%n", totalPendapatan);
    }

    private static void tampilkanStokMenipis() {
        System.out.println("\n--- PRODUK DENGAN STOK MENIPIS (<10 unit) ---");
        boolean adaData = false;
        for (Product p : daftarProduk) {
            if (p.getStock() < 10) {
                System.out.println(p);
                adaData = true;
            }
        }
        if (!adaData) {
            System.out.println("Tidak ada produk dengan stok menipis saat ini.");
        }
    }

    private static void tampilkanProdukTerlaris() {
        List<String> idProdukUnik = new ArrayList<>();
        List<Integer> totalTerjual = new ArrayList<>();

        for (transaction t : daftarTransaksi) {
            for (transactionitem item : t.getItems()) {
                String id = item.getProduct().getId();
                int indeks = idProdukUnik.indexOf(id);
                if (indeks == -1) {
                    idProdukUnik.add(id);
                    totalTerjual.add(item.getQuantity());
                } else {
                    totalTerjual.set(indeks, totalTerjual.get(indeks) + item.getQuantity());
                }
            }
        }

        for (int i = 0; i < totalTerjual.size() - 1; i++) {
            int indeksMax = i;
            for (int j = i + 1; j < totalTerjual.size(); j++) {
                if (totalTerjual.get(j) > totalTerjual.get(indeksMax)) {
                    indeksMax = j;
                }
            }
            int tempTotal = totalTerjual.get(i);
            totalTerjual.set(i, totalTerjual.get(indeksMax));
            totalTerjual.set(indeksMax, tempTotal);

            String tempId = idProdukUnik.get(i);
            idProdukUnik.set(i, idProdukUnik.get(indeksMax));
            idProdukUnik.set(indeksMax, tempId);
        }

        
        System.out.println("\n--- 3 PRODUK TERLARIS ---");
        if (idProdukUnik.isEmpty()) {
            System.out.println("Belum ada transaksi yang tercatat.");
            return;
        }

        int batas = Math.min(3, idProdukUnik.size());
        for (int i = 0; i < batas; i++) {
            try {
                Product p = cariProdukById(idProdukUnik.get(i));
                System.out.println((i + 1) + ". " + p.getName() + " - " + totalTerjual.get(i) + " unit terjual");
            } catch (NotFoundException e) {
                System.out.println((i + 1) + ". [Produk tidak ditemukan] - " + totalTerjual.get(i) + " unit terjual");
            }
        }
    }
}
