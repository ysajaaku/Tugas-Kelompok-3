
public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah > this.stock) {
            throw new IllegalArgumentException(
                    "Stok tidak cukup untuk produk " + name + ". Sisa stok: " + stock);
        }
        this.stock -= jumlah;
    }


    public void tambahStok(int jumlah) {
        this.stock += jumlah;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-20s | %-12s | Rp%,10.2f | Stok: %d",
                id, name, category, price, stock);
    }
}
