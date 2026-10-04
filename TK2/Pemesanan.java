
public abstract sealed class Pemesanan implements Pembayaran
        permits FlightPemesanan, HotelPemesanan{

    private int confirmationNumber;
    private String customerName;
    private String contact;

    public Pemesanan(int confirmationNumber, String customerName, String contact) {
        this.confirmationNumber = confirmationNumber;
        this.customerName = customerName;
        this.contact = contact;
    }

    public int getConfirmationNumber() { return confirmationNumber; }
    public String getCustomerName() { return customerName; }
    public String getContact() { return contact; }

    public abstract void display();
}
