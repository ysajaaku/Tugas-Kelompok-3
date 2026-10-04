import java.util.Random;

public final class NumberGenerator {

    public static int generate() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
