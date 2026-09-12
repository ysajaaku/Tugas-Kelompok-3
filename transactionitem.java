
public class transactionitem {
    private Product product;
    private int quantity;

    public transactionitem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Menghitung subtotal harga untuk item ini (harga satuan x jumlah).
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-20s x%-3d Rp%,10.2f", product.getName(), quantity, getSubtotal());
    }
}
