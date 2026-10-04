
public class PemesananNotFoundException extends Exception {

    public PemesananNotFoundException(int confirmationNumber) {
        super("Reservasi dengan nomor " + confirmationNumber + " tidak ditemukan.");
    }
}
