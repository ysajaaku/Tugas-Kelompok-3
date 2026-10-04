import java.time.LocalDate;
import java.util.ArrayList;

public class TravelApp {

    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Hotel> hotels = new ArrayList<>();
    private ArrayList<Pemesanan> reservations = new ArrayList<>();

    public TravelApp() {
         for (int i = 1; i <= 30; i++) {
            LocalDate tanggal = LocalDate.now().plusDays(i);

        flights.add(new Flight("GA101", "Jakarta", "Bali", tanggal, "08:00", "10:50", 1250000, 40));
        flights.add(new Flight("JT202", "Jakarta", "Bali", tanggal, "13:30", "16:20", 980000, 25));
        flights.add(new Flight("QG303", "Surabaya", "Jakarta", tanggal, "09:15", "10:40", 870000, 30));
         }
        // LocalDate besok = LocalDate.now().plusDays(7); //

        // flights.add(new Flight("GA101", "Jakarta", "Bali", besok, "08:00", "10:50", 1250000, 40));
        // flights.add(new Flight("JT202", "Jakarta", "Bali", besok, "13:30", "16:20", 980000, 25));
        // flights.add(new Flight("QG303", "Surabaya", "Jakarta", besok, "09:15", "10:40", 870000, 30));

        hotels.add(new Hotel("H01", "Bali Beach Resort", "Bali", 850000, 10));
        hotels.add(new Hotel("H02", "Kuta Inn", "Bali", 420000, 15));
        hotels.add(new Hotel("H03", "Jakarta Central Hotel", "Jakarta", 600000, 20));
    }

    public ArrayList<Flight> searchFlights(String origin, String destination, LocalDate date, int passengers) {
        ArrayList<Flight> hasil = new ArrayList<>();

        for (Flight f : flights) {
            boolean asalCocok = f.getOrigin().equalsIgnoreCase(origin);
            boolean tujuanCocok = f.getDestination().equalsIgnoreCase(destination);
            boolean tanggalCocok = f.getDate().equals(date);
            boolean kursiCukup = f.getAvailableSeats() >= passengers;

            if (asalCocok && tujuanCocok && tanggalCocok && kursiCukup) {
                hasil.add(f);
            }
        }


        hasil.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));

        return hasil;
    }


    public ArrayList<Hotel> searchHotels(String location) {
        ArrayList<Hotel> hasil = new ArrayList<>();

        for (Hotel h : hotels) {
            if (h.getLocation().equalsIgnoreCase(location) && h.getAvailableRooms() > 0) {
                hasil.add(h);
            }
        }


        hasil.sort((a, b) -> Double.compare(a.getPricePerNight(), b.getPricePerNight()));

        return hasil;
    }


    public FlightPemesanan bookFlight(String flightNumber, String name, String contact, int passengers) {
        Flight dipilih = null;
        for (Flight f : flights) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                dipilih = f;
            }
        }

        if (dipilih == null) {
            throw new IllegalArgumentException("Nomor penerbangan tidak ditemukan: " + flightNumber);
        }
        if (dipilih.getAvailableSeats() < passengers) {
            throw new IllegalArgumentException("Kursi tidak mencukupi.");
        }

        dipilih.setAvailableSeats(dipilih.getAvailableSeats() - passengers);

        int nomor = createUniqueConfirmationNumber();
        FlightPemesanan reservasi = new FlightPemesanan (nomor, name, contact, dipilih, passengers);
        reservations.add(reservasi);

        return reservasi;
    }

    public HotelPemesanan bookHotel(String hotelId, String name, String contact,
                                      LocalDate checkIn, LocalDate checkOut, int guests) {
        Hotel dipilih = null;
        for (Hotel h : hotels) {
            if (h.getHotelId().equalsIgnoreCase(hotelId)) {
                dipilih = h;
            }
        }

        if (dipilih == null) {
            throw new IllegalArgumentException("ID hotel tidak ditemukan: " + hotelId);
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Tanggal check-out harus setelah check-in.");
        }
        if (dipilih.getAvailableRooms() <= 0) {
            throw new IllegalArgumentException("Kamar tidak tersedia.");
        }

        dipilih.setAvailableRooms(dipilih.getAvailableRooms() - 1);

        int nomor = createUniqueConfirmationNumber();
        HotelPemesanan reservasi = new HotelPemesanan (nomor, name, contact, dipilih, checkIn, checkOut, guests);
        reservations.add(reservasi);

        return reservasi;
    }

    public void cancelReservation(int confirmationNumber) throws PemesananNotFoundException{
        Pemesanan ditemukan = null;
        for (Pemesanan r : reservations) {
            if (r.getConfirmationNumber() == confirmationNumber) {
                ditemukan = r;
            }
        }

        if (ditemukan == null) {
            throw new PemesananNotFoundException(confirmationNumber);
        }

        if (ditemukan instanceof FlightPemesanan fr) {
            Flight f = fr.getFlight();
            f.setAvailableSeats(f.getAvailableSeats() + fr.getPassengers());
        } else if (ditemukan instanceof HotelPemesanan hr) {
            Hotel h = hr.getHotel();
            h.setAvailableRooms(h.getAvailableRooms() + 1);
        }


        reservations.remove(ditemukan);
    }

    public ArrayList<Pemesanan> getReservations() {
        return reservations;
    }

    // Membuat nomor acak, diulang sampai tidak bentrok dengan nomor yang sudah ada
    private int createUniqueConfirmationNumber() {
        int nomor = NumberGenerator.generate();
        while (isNumberUsed(nomor)) {
            nomor = NumberGenerator.generate();
        }
        return nomor;
    }

    private boolean isNumberUsed(int nomor) {
        for (Pemesanan r : reservations) {
            if (r.getConfirmationNumber() == nomor) {
                return true;
            }
        }
        return false;
    }
}
