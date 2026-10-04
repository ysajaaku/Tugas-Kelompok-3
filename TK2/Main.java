import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TravelApp app = new TravelApp();
        boolean berjalan = true;

        while (berjalan) {
            tampilkanMenu();
            int pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    menuCariPenerbangan(app);
                    break;
                case 2:
                    menuCariHotel(app);
                    break;
                case 3:
                    menuPesanPenerbangan(app);
                    break;
                case 4:
                    menuPesanHotel(app);
                    break;
                case 5:
                    menuBatalkan(app);
                    break;
                case 6:
                    menuLihatSemua(app);
                    break;
                case 7:
                    System.out.println("Terima kasih!");
                    berjalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak ada. Pilih 1-7.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("===== SISTEM PEMESANAN PERJALANAN =====");
        System.out.println("1. Cari Penerbangan");
        System.out.println("2. Cari Hotel");
        System.out.println("3. Pesan Penerbangan");
        System.out.println("4. Pesan Hotel");
        System.out.println("5. Batalkan Reservasi");
        System.out.println("6. Lihat Semua Pemesanan");
        System.out.println("7. Keluar");
    }

    private static void menuCariPenerbangan(TravelApp app) {
        System.out.println("Contoh: rute Jakarta -> Bali dan Surabaya -> Jakarta,");
        System.out.println("tanggal " + LocalDate.now().plusDays(1) + " sampai " + LocalDate.now().plusDays(30) + ".");
        String asal = bacaTeks("Kota asal: ");
        String tujuan = bacaTeks("Kota tujuan: ");
        LocalDate tanggal = bacaTanggal("Tanggal berangkat");
        int penumpang = bacaAngka("Jumlah penumpang: ");

        ArrayList<Flight> hasil = app.searchFlights(asal, tujuan, tanggal, penumpang);

        if (hasil.isEmpty()) {
            System.out.println("Tidak ada penerbangan tersedia.");
            System.out.println("Tidak ada penerbangan tersedia.");
            System.out.println("Cek lagi: ejaan kota, tanggal (yyyy-MM-dd), dan jumlah penumpang.");
        } else {
            for (Flight f : hasil) {
                System.out.println(f);
            }
        }
    }

    private static void menuCariHotel(TravelApp app) {
        String kota = bacaTeks("Kota: ");

        ArrayList<Hotel> hasil = app.searchHotels(kota);

        if (hasil.isEmpty()) {
            System.out.println("Tidak ada hotel tersedia.");
        } else {
            for (Hotel h : hasil) {
                System.out.println(h);
            }
        }
    }



    private static void menuPesanPenerbangan(TravelApp app) {
        String nomorPenerbangan = bacaTeks("Nomor penerbangan (contoh GA101): ");
        String nama = bacaTeks("Nama penumpang: ");
        String kontak = bacaTeks("Kontak: ");
        int penumpang = bacaAngka("Jumlah penumpang: ");

        try {
            FlightPemesanan hasil = app.bookFlight(nomorPenerbangan, nama, kontak, penumpang);
            System.out.println("Pemesanan berhasil!");
            hasil.display();
        } catch (IllegalArgumentException e) {
            // e.getMessage() berisi teks yang kita tulis saat "throw" di TravelApp
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void menuPesanHotel(TravelApp app) {
        String idHotel = bacaTeks("ID hotel (contoh H01): ");
        String nama = bacaTeks("Nama tamu: ");
        String kontak = bacaTeks("Kontak: ");
        LocalDate checkIn = bacaTanggal("Tanggal check-in");
        LocalDate checkOut = bacaTanggal("Tanggal check-out");
        int tamu = bacaAngka("Jumlah tamu: ");

        try {
            HotelPemesanan hasil = app.bookHotel(idHotel, nama, kontak, checkIn, checkOut, tamu);
            System.out.println("Pemesanan berhasil!");
            hasil.display();
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }


    private static void menuBatalkan(TravelApp app) {
        int nomor = bacaAngka("Nomor konfirmasi: ");

        try {
            app.cancelReservation(nomor);
            System.out.println("Reservasi berhasil dibatalkan.");
        } catch (PemesananNotFoundException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }


    private static void menuLihatSemua(TravelApp app) {
        ArrayList<Pemesanan> semua = app.getReservations();

        if (semua.isEmpty()) {
            System.out.println("Belum ada pemesanan.");
        } else {
            for (Pemesanan r : semua) {
                r.display();
            }
        }
    }



    private static String bacaTeks(String pertanyaan) {
        System.out.print(pertanyaan);
        return scanner.nextLine().trim();
    }

    private static int bacaAngka(String pertanyaan) {
        while (true) { 
            System.out.print(pertanyaan);
            String teks = scanner.nextLine().trim();
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Coba lagi.");
            }
        }
    }

    private static LocalDate bacaTanggal(String pertanyaan) {
        while (true) {
            System.out.print(pertanyaan + " (format yyyy-MM-dd, contoh 2026-12-31): ");
            String teks = scanner.nextLine().trim();
            try {
                return LocalDate.parse(teks);
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah. Coba lagi.");
            }
        }
    }
}
