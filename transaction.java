import java.util.ArrayList;
import java.util.List;

public class transaction {

    private static final double AMBANG_DISKON = 500_000.0;
    private static final double PERSEN_DISKON = 0.10;

    private String id;
    private List<transactionitem> items;
    private String paymentMethod;

    public transaction(String id, String paymentMethod) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<transactionitem> getItems() {
        return items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void tambahItem(transactionitem item) {
        items.add(item);
    }

    /**
     * Menghitung berapa unit sebuah produk yang sudah ada di dalam keranjang.
     * Dipakai untuk mengecek sisa stok yang masih boleh dibeli.
     */
    public int getJumlahDiKeranjang(String idProduk) {
        int total = 0;
        for (transactionitem item : items) {
            if (item.getProduct().getId().equalsIgnoreCase(idProduk)) {
                total += item.getQuantity();
            }
        }
        return total;
    }

    /**
     * Menghapus seluruh item dengan ID produk tertentu dari keranjang.
     *
     * @return true bila ada item yang terhapus
     */
    public boolean hapusItem(String idProduk) {
        boolean adaYangDihapus = false;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).getProduct().getId().equalsIgnoreCase(idProduk)) {
                items.remove(i);
                adaYangDihapus = true;
            }
        }
        return adaYangDihapus;
    }

    /**
     * Memotong stok seluruh produk di keranjang.
     *
     * Method ini hanya dipanggil setelah pembayaran dikonfirmasi, sehingga
     * transaksi yang dibatalkan tidak mengurangi stok. Seluruh item diperiksa
     * lebih dulu sebelum satu pun stok dipotong, agar tidak terjadi kondisi
     * setengah jadi bila ada produk yang stoknya ternyata tidak cukup.
     */
    public void potongStok() {
        for (transactionitem item : items) {
            Product produk = item.getProduct();
            if (getJumlahDiKeranjang(produk.getId()) > produk.getStock()) {
                throw new IllegalArgumentException("Stok tidak cukup untuk produk "
                        + produk.getName() + ". Sisa stok: " + produk.getStock());
            }
        }
        for (transactionitem item : items) {
            item.getProduct().kurangiStok(item.getQuantity());
        }
    }

    public double hitungSubtotalKotor() {
        double total = 0;
        for (transactionitem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double hitungDiskon() {
        double subtotal = hitungSubtotalKotor();
        if (subtotal >= AMBANG_DISKON) {
            return subtotal * PERSEN_DISKON;
        }
        return 0.0;
    }

    public double hitungTotalAkhir() {
        return hitungSubtotalKotor() - hitungDiskon();
    }


    public int getTotalUnit() {
        int total = 0;
        for (transactionitem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    public void cetakStruk() {
        System.out.println("========================================");
        System.out.println("           STRUK TRANSAKSI");
        System.out.println("========================================");
        System.out.println("No. Transaksi : " + id);
        for (transactionitem item : items) {
            System.out.println(item);
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal      : Rp%,.2f%n", hitungSubtotalKotor());
        System.out.printf("Diskon        : Rp%,.2f%n", hitungDiskon());
        System.out.printf("Total Bayar   : Rp%,.2f%n", hitungTotalAkhir());
        System.out.println("Metode Bayar  : " + paymentMethod);
        System.out.println("========================================");
        System.out.println("      Terima kasih telah berbelanja!");
        System.out.println("========================================");
    }
}
