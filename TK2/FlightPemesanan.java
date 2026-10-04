
public final class FlightPemesanan extends Pemesanan {

    private Flight flight;     
    private int passengers;    

    public FlightPemesanan(int confirmationNumber, String customerName, String contact,
                             Flight flight, int passengers) {
        super(confirmationNumber, customerName, contact); 
        this.flight = flight;
        this.passengers = passengers;
    }

    public Flight getFlight() { return flight; }
    public int getPassengers() { return passengers; }

    
    @Override
    public double getTotalPrice() {
        return flight.getPrice() * passengers;
    }

   
    @Override
    public void display() {
        System.out.println("[PENERBANGAN] Nomor konfirmasi: " + getConfirmationNumber());
        System.out.println("  Penumpang  : " + getCustomerName() + " (" + getContact() + "), " + passengers + " orang");
        System.out.println("  Penerbangan: " + flight);
        System.out.println("  Total harga: Rp" + getTotalPrice());
    }
}
