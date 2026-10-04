import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class HotelPemesanan extends Pemesanan {
   private Hotel hotel;
   private LocalDate checkIn;
   private LocalDate checkOut;
   private int guests;

   public HotelPemesanan(int var1, String var2, String var3, Hotel var4, LocalDate var5, LocalDate var6, int var7) {
      super(var1, var2, var3);
      this.hotel = var4;
      this.checkIn = var5;
      this.checkOut = var6;
      this.guests = var7;
   }

   public Hotel getHotel() {
      return this.hotel;
   }

   public long getNights() {
      return ChronoUnit.DAYS.between(this.checkIn, this.checkOut);
   }

   public double getTotalPrice() {
      return this.hotel.getPricePerNight() * (double)this.getNights();
   }

   public void display() {
      System.out.println("[HOTEL] Nomor konfirmasi: " + this.getConfirmationNumber());
      PrintStream var10000 = System.out;
      String var10001 = this.getCustomerName();
      var10000.println("  Tamu    : " + var10001 + " (" + this.getContact() + "), " + this.guests + " orang");
      System.out.println("  Hotel   : " + String.valueOf(this.hotel));
      var10000 = System.out;
      var10001 = String.valueOf(this.checkIn);
      var10000.println("  Menginap: " + var10001 + " sampai " + String.valueOf(this.checkOut) + " (" + this.getNights() + " malam)");
      System.out.println("  Total   : Rp" + this.getTotalPrice());
   }
}
