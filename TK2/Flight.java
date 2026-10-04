import java.time.LocalDate;

public class Flight {

    // ===== Data (field) =====
    private String flightNumber;
    private String origin;        
    private String destination;   
    private LocalDate date;       
    private String departureTime; 
    private String arrivalTime;   
    private double price;         
    private int availableSeats;   

    
    public Flight(String flightNumber, String origin, String destination, LocalDate date,
                  String departureTime, String arrivalTime, double price, int availableSeats) {
       
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDate getDate() { return date; }
    public double getPrice() { return price; }
    public int getAvailableSeats() { return availableSeats; }

    
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString() {
        return flightNumber + " | " + origin + " -> " + destination
                + " | " + date + " " + departureTime + "-" + arrivalTime
                + " | Rp" + price + " | Kursi: " + availableSeats;
    }
}
